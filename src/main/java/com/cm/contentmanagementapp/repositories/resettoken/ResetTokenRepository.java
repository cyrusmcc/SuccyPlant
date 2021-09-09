package com.cm.contentmanagementapp.repositories.resettoken;

import com.cm.contentmanagementapp.models.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {

    ResetToken findByToken(String token);

    boolean existsByToken(String token);

}
