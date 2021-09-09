package com.cm.contentmanagementapp.services.resettoken;

import com.cm.contentmanagementapp.controllers.SettingsController;
import com.cm.contentmanagementapp.models.EmailResetToken;
import com.cm.contentmanagementapp.models.ResetToken;
import com.cm.contentmanagementapp.repositories.resettoken.EmailResetTokenRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class EmailResetTokenServiceImpl implements EmailResetTokenService {

    private EmailResetTokenRepository tokenRepository;

    private ResetTokenService resetTokenService;

    private static final Logger log = LoggerFactory.getLogger(EmailResetTokenServiceImpl.class);


    @Autowired
    public EmailResetTokenServiceImpl(EmailResetTokenRepository tokenRepository, ResetTokenService resetTokenService) {
        this.tokenRepository = tokenRepository;
        this.resetTokenService = resetTokenService;
    }


    @Override
    public Optional<EmailResetToken> findByResetToken(ResetToken resetToken) throws NotFoundException {
        Optional<EmailResetToken> emailResetToken = null;

        if (tokenRepository.findByResetToken(resetToken) != null) {

            emailResetToken = tokenRepository.findByResetToken(resetToken);

        } else {
            throw new NotFoundException("Email reset token not found");
        }

        return emailResetToken;
    }

    public Optional<EmailResetToken> findById(long id) throws NoSuchAlgorithmException {

        if (tokenRepository.findById(id) == null) {
            log.info("No password reset token found with id: {}", id);
            return null;
        }
        return tokenRepository.findById(id);
    }

    @Override
    public void save(EmailResetToken emailToken) {

        resetTokenService.save(emailToken.getResetToken());
        tokenRepository.save(emailToken);

    }

    @Override
    public void delete(EmailResetToken token) {
        tokenRepository.delete(token);
    }
}
