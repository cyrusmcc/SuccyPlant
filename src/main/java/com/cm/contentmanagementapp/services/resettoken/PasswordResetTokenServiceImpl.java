package com.cm.contentmanagementapp.services.resettoken;

import com.cm.contentmanagementapp.models.PasswordResetToken;
import com.cm.contentmanagementapp.models.ResetToken;
import com.cm.contentmanagementapp.repositories.resettoken.PasswordResetTokenRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private PasswordResetTokenRepository tokenRepository;

    private ResetTokenService resetTokenService;

    private static final Logger log = LoggerFactory.getLogger(PasswordResetTokenServiceImpl.class);


    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository tokenRepository, ResetTokenService resetTokenService) {
        this.tokenRepository = tokenRepository;
        this.resetTokenService = resetTokenService;
    }

    @Override
    public Optional<PasswordResetToken> findByResetToken(ResetToken resetToken) throws NotFoundException {

        Optional<PasswordResetToken> passwordResetToken = null;

        if (tokenRepository.findByResetToken(resetToken) != null) {

            passwordResetToken = tokenRepository.findByResetToken(resetToken);

        } else {
            throw new NotFoundException("Password reset token not found");
        }

        return passwordResetToken;
    }

    @Override
    public Optional<PasswordResetToken> findById(Long id) throws NoSuchAlgorithmException {

        if (tokenRepository.findById(id) == null) {
            log.info("No password reset token found with id: {}", id);
            return null;
        }
        return tokenRepository.findById(id);
    }

    @Override
    public void save(PasswordResetToken passToken) {

        resetTokenService.save(passToken.getResetToken());
        tokenRepository.save(passToken);

    }

    @Override
    public void delete(PasswordResetToken token) {
        tokenRepository.delete(token);
    }
}
