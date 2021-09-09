package com.cm.contentmanagementapp.services.resettoken;

import com.cm.contentmanagementapp.models.EmailResetToken;
import com.cm.contentmanagementapp.models.ResetToken;
import javassist.NotFoundException;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface EmailResetTokenService {

    Optional<EmailResetToken> findByResetToken(ResetToken resetToken) throws NotFoundException;

    Optional<EmailResetToken> findById(long id) throws NoSuchAlgorithmException;

    void save(EmailResetToken token);

    void delete(EmailResetToken token);
}
