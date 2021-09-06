package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.PasswordResetToken;
import com.cm.contentmanagementapp.repositories.PasswordResetTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }


    @Override
    public String hashToken(String token) throws NoSuchAlgorithmException {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte hashBytes[] = messageDigest.digest(token.getBytes(StandardCharsets.UTF_8));
            BigInteger noHash = new BigInteger(1, hashBytes);
            String hashToken = noHash.toString(16);
            return hashToken;
        } catch(NoSuchAlgorithmException nsae) {
            log.info("No such hash algo: {}", nsae);
        }

        return null;
    }

    @Override
    public PasswordResetToken findByToken(String unhashedToken) throws NoSuchAlgorithmException {

        if(!existsByToken(unhashedToken)) {
            return null;
        }

        return tokenRepository.findByToken(hashToken(unhashedToken));
    }

    @Override
    public boolean existsByToken(String unhashedToken) throws NoSuchAlgorithmException {

        String hashedToken = hashToken(unhashedToken);
        if(!tokenRepository.existsByToken(hashedToken)) {
            log.info("Invalid hash token: {}", hashedToken);
            return false;
        }

        return true;
    }

    @Override
    public void save(PasswordResetToken token) {

        tokenRepository.save(token);

    }
}
