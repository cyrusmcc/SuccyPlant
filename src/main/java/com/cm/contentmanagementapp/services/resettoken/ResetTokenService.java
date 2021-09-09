package com.cm.contentmanagementapp.services.resettoken;

import com.cm.contentmanagementapp.models.ResetToken;

import java.security.NoSuchAlgorithmException;

public interface ResetTokenService {

    ResetToken findByToken(String token) throws NoSuchAlgorithmException;

    boolean existsByToken(String token) throws NoSuchAlgorithmException;

    void save(ResetToken token);

    String hashToken(String token) throws NoSuchAlgorithmException;

}
