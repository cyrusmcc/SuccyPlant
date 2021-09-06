package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.PasswordResetToken;

import java.security.NoSuchAlgorithmException;

public interface PasswordResetTokenService {

    String hashToken(String token) throws NoSuchAlgorithmException;

    PasswordResetToken findByToken(String token) throws NoSuchAlgorithmException;

    boolean existsByToken(String token) throws NoSuchAlgorithmException;

    void save(PasswordResetToken token);

}
