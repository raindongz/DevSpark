package com.devspark.authservice.service.impl;

import com.devspark.authservice.constants.DeleteFlags;
import com.devspark.authservice.entity.AuthEntity;
import com.devspark.authservice.exception.customExceptions.IncorrectPasswordException;
import com.devspark.authservice.exception.customExceptions.JWTSignException;
import com.devspark.authservice.exception.customExceptions.UserNotFoundException;
import com.devspark.authservice.pojo.dto.CreateUserDTO;
import com.devspark.authservice.pojo.dto.CreateUserProfileDTO;
import com.devspark.authservice.pojo.dto.LoginUserDTO;
import com.devspark.authservice.pojo.mapper.UserMapper;
import com.devspark.authservice.pojo.vo.CreateUserVO;
import com.devspark.authservice.pojo.vo.LoginUserVO;
import com.devspark.authservice.repository.AuthRepository;
import com.devspark.authservice.service.AuthService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
//@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    public AuthServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Value("${rsa.private-key}")
    private String rsaPrivateKey;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateUserVO createUser(CreateUserDTO createUserDTO) {
        // 1. copy the bean properties to auth entity
         AuthEntity authEntity = UserMapper.INSTANCE.userDTOToAuthEntity(createUserDTO);

        // 2. set rest of entity properties
        authEntity.setDeletedFlag(DeleteFlags.NOT_DELETED);
        authEntity.setPasswordChangedAt(Date.from(Instant.parse("0001-01-01T00:00:00Z")));
        authEntity.setCreatedAt(new Date());
        authEntity.setUpdatedAt(new Date());

        // 3. save the entity in this Auth Service
        AuthEntity save = authRepository.save(authEntity);

        // 4. copy the properties of CreateUserProfileDTO. call User micro Service to save the user profile
        CreateUserProfileDTO createUserProfileDTO = UserMapper.INSTANCE.userDTOToUserProfile(createUserDTO);

        // 5. set rest of entity properties
        createUserProfileDTO.setUserId(save.getId());
        createUserProfileDTO.setDeletedFlag(DeleteFlags.NOT_DELETED);
        createUserProfileDTO.setCreatedAt(new Date());
        createUserProfileDTO.setUpdatedAt(new Date());

        // 6. calculate and set the total score of user
        Integer totalPoints = calculatePoints(createUserDTO);
        createUserProfileDTO.setTotalScore(totalPoints);

        // 7. call user service to store user profile


        // 5. return success if no error
        return new CreateUserVO(true);
    }


    private Integer calculatePoints(CreateUserDTO createUserDTO) {

        // 1. age is 5% of total points
        Integer agePoint = (int) (((double) createUserDTO.getAge() / 80) * 5);

        // 2. job title is 50% of total points
        Integer jobPoint = (int) (((double) createUserDTO.getJobTitle() / 10) * 50);

        // 3. favourite language is 20% if total points
        Integer languagePoint = (int) (((double) createUserDTO.getFavLanguage() / 15) * 20);

        // 4. mbtiType is 10% of total points
        Integer mbtiPoint = (int) (((double) createUserDTO.getMbtiType() / 15) * 10);

        // 5. technical tags is 10% of total points
        int techListTotalPoints = 1;
        String[] techList = createUserDTO.getTechnicalTags().split(",");
        for (String each : techList) {
            try {
                techListTotalPoints += Integer.parseInt(each);
            } catch (NumberFormatException e) {
                log.error("convert technical tags string to number fail {}", e.getMessage());
                throw new NumberFormatException(e.getMessage());
            }
        }
        Integer techPoint = (int) (((double) techListTotalPoints / 30) * 10);

        // 6. non-technical tags is 5% of total points
        int nonTechListTotalPoints = 1;
        String[] nonTechList = createUserDTO.getNonTechTags().split(",");
        for (String each : nonTechList) {
            try {
                nonTechListTotalPoints += Integer.parseInt(each);
            } catch (NumberFormatException e) {
                log.error("convert non-technical tags string to number fail {}", e.getMessage());
                throw new NumberFormatException("convert string to number fail");
            }
        }
        Integer nonTechPoint = (int) (((double) nonTechListTotalPoints / 30) * 5);

        // return total points
        return nonTechPoint
                + techPoint
                + mbtiPoint
                + languagePoint
                + jobPoint
                + agePoint;
    }


    @Override
    public LoginUserVO loginUser(LoginUserDTO request) {
        // 1. check if user exist
        Optional<AuthEntity> userInfo = authRepository.getUserInfoEntityByUsername(request.username());
        log.debug("username :" + request.username());
        if (userInfo.isEmpty()) {
            throw new UserNotFoundException("User not exist");
        }

        // 2. if user exist, check if password identical
        if (!request.password().equals(userInfo.get().getHashedPassword())) {
            throw new IncorrectPasswordException("password incorrect");
        }

        // 3. every thing is good, generate token and return
        String token = generateToken(userInfo.get().getId());
        return new LoginUserVO(token, "200");
    }

    private String generateToken(Long userId) {

        KeyFactory kf = null;
        PrivateKey privKey;
        try {
            kf = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            log.error("key factory initialize exception: "+ e);
            throw new JWTSignException("log in failed, sign token error");
        }

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey));
        try {
            privKey = kf.generatePrivate(keySpecPKCS8);
        } catch (InvalidKeySpecException e) {
            log.error("generate private key spec exception: "+ e);
            throw new JWTSignException("log in failed, sign token error");
        }

        // Create RSA-signer with the private key
        JWSSigner signer = new RSASSASigner(privKey);

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userId.toString())
                .issuer("https://devspark.com")
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).build(),
                claimsSet);

        // Compute the RSA signature
        try {
            signedJWT.sign(signer);
        } catch (JOSEException e) {
            log.error("sign jwt exception: "+ e);
            throw new JWTSignException("log in failed, sign token error");
        }
        return signedJWT.serialize();
    }
}
