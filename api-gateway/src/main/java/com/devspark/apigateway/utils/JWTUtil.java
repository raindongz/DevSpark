package com.devspark.apigatway.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JWTUtil {

    @Value("${rsa.public-key}")
    private String publicKey;

    public JWTClaimsSet getClaimSet(String token){
        token = token.replaceFirst("Bearer ", "");
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            log.error("build key factory failed: " + e);
            throw new RuntimeException(e);
        }
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        RSAPublicKey pubKey = null;
        try {
            pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
        } catch (InvalidKeySpecException e) {
            log.error("build key spec failed: " + e);
            throw new RuntimeException(e);
        }
        // Decrypt
        SignedJWT signedJWT = null;
        try {
            signedJWT = SignedJWT.parse(token);
        } catch (ParseException e) {
            log.error(" parse token failed: " + e);
            throw new RuntimeException(e);
        }
        JWSVerifier verifier = new RSASSAVerifier(pubKey);

        boolean verify;
        try {
            verify = signedJWT.verify(verifier);
        } catch (JOSEException e) {
            log.error(" verify token failed: " + e);
            throw new RuntimeException(e);
        }

        if (!verify){
            log.error("not a valid token");
            return null;
        }

        JWTClaimsSet claimSet;
        try {
            claimSet = signedJWT.getJWTClaimsSet();
        } catch (ParseException e) {
            log.error("get jwt claim set failed: " + e );
            throw new RuntimeException(e);
        }

        if (claimSet.getExpirationTime().before(new Date())){
            log.error("invalid jwt token");
            return null;
        }

        return claimSet;
    }
}
