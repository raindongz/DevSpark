package com.devspark.userservice.service.impl;

import com.devspark.userservice.constants.DeleteFlags;
import com.devspark.userservice.entity.UserInfoEntity;
import com.devspark.userservice.exception.customExceptions.IncorrectPasswordException;
import com.devspark.userservice.exception.customExceptions.JWTSignException;
import com.devspark.userservice.exception.customExceptions.UserNotFoundException;
import com.devspark.userservice.pojo.dto.CreateUserDTO;
import com.devspark.userservice.pojo.dto.LoginUserDTO;
import com.devspark.userservice.pojo.mapper.UserMapper;
import com.devspark.userservice.pojo.vo.CreateUserVO;
import com.devspark.userservice.pojo.vo.LoginUserVO;
import com.devspark.userservice.repository.UserInfoRepository;
import com.devspark.userservice.service.UserInfoService;
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
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Value("${rsa.private-key}")
    private String rsaPrivateKey;

    @Override
    public CreateUserVO createUser(CreateUserDTO createUserDTO) {
        // 1. copy the bean properties to entity
        UserInfoEntity userInfoEntity = UserMapper.INSTANCE.userDTOToEntity(createUserDTO);

        // 2. set rest of entity properties
        userInfoEntity.setDeletedFlag(DeleteFlags.NOT_DELETED);
        userInfoEntity.setPasswordChangedAt(Date.from(Instant.parse("0001-01-01T00:00:00Z")));
        userInfoEntity.setCreatedAt(new Date());
        userInfoEntity.setUpdatedAt(new Date());

        // 3. calculate and set the total score of user
        Integer totalPoints = calculatePoints(createUserDTO);
        userInfoEntity.setTotalScore(totalPoints);

        // 4. save the entity
        userInfoRepository.save(userInfoEntity);

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
        Optional<UserInfoEntity> userInfo = userInfoRepository.getUserInfoEntityByUsername(request.username());
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
