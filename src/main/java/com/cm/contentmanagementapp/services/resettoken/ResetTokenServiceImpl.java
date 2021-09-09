package com.cm.contentmanagementapp.services.resettoken;

import com.cm.contentmanagementapp.models.ResetToken;
import com.cm.contentmanagementapp.repositories.resettoken.ResetTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class ResetTokenServiceImpl implements ResetTokenService{

    private ResetTokenRepository tokenRepository;

    private static final Logger log = LoggerFactory.getLogger(PasswordResetTokenServiceImpl.class);

    @Autowired
    public ResetTokenServiceImpl(ResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public ResetToken findByToken(String token) throws NoSuchAlgorithmException {

        if(!existsByToken(token)) {
            return null;
        }

        return tokenRepository.findByToken(hashToken(token));
    }

    @Override
    public boolean existsByToken(String token) throws NoSuchAlgorithmException {

        String hashedToken = hashToken(token);
        if(!tokenRepository.existsByToken(hashedToken)) {
            log.info("Invalid hash token: {}", hashedToken);
            return false;
        }

        return true;
    }

    @Override
    public void save(ResetToken token) {
        tokenRepository.save(token);
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
}
