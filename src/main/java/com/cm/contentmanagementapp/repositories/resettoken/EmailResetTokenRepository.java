package com.cm.contentmanagementapp.repositories.resettoken;

import com.cm.contentmanagementapp.models.EmailResetToken;
import com.cm.contentmanagementapp.models.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailResetTokenRepository extends JpaRepository<EmailResetToken, Long> {

    Optional<EmailResetToken> findByResetToken(ResetToken resetToken);

    Optional<EmailResetToken> findById(Long id);

}
