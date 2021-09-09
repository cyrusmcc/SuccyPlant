package com.cm.contentmanagementapp.services.resettoken;

import com.cm.contentmanagementapp.models.PasswordResetToken;
import com.cm.contentmanagementapp.models.ResetToken;
import javassist.NotFoundException;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface PasswordResetTokenService {

    Optional<PasswordResetToken> findByResetToken(ResetToken resetToken) throws NotFoundException;

    Optional<PasswordResetToken> findById(Long id) throws NoSuchAlgorithmException;

    void save(PasswordResetToken token);

    void delete(PasswordResetToken token);

}
