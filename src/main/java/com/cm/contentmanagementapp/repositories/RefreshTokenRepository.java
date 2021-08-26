package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.RefreshToken;
import com.cm.contentmanagementapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Override
    Optional<RefreshToken> findById(Long id);

    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);
}
