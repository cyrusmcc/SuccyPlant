package com.cm.contentmanagementapp.repositories.resettoken;

import com.cm.contentmanagementapp.models.PasswordResetToken;
import com.cm.contentmanagementapp.models.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByResetToken(ResetToken resetToken);

    Optional<PasswordResetToken> findById(Long id);

}
