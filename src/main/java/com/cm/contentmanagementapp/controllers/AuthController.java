package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.*;
import com.cm.contentmanagementapp.payload.request.*;
import com.cm.contentmanagementapp.services.*;
import com.cm.contentmanagementapp.TokenRefreshException;
import com.cm.contentmanagementapp.payload.response.JwtResponse;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.payload.response.TokenRefreshResponse;
import com.cm.contentmanagementapp.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authManager;

    private UserService userService;

    private PasswordEncoder encoder;

    private JwtUtils jwtUtils;

    private RefreshTokenService refreshTokenService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public AuthController(AuthenticationManager authManager, UserService userService,
                          PasswordEncoder encoder, JwtUtils utils, RefreshTokenService refreshTokenService) {
        this.authManager = authManager;
        this.userService = userService;
        this.encoder = encoder;
        this.jwtUtils = utils;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        log.info("User login attempt: {}", loginRequest.getUsername());

        return ResponseEntity.ok(new JwtResponse(jwt,
                refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getUserJoinDate(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

        if (userService.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken."));
        }

        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email already in use"));
        }

        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        userService.newUser(user);

        log.info("User register attempt: {} ", user.getId());

        return ResponseEntity.ok(new MessageResponse("User registered successfully."));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {

        if (request.getRefreshToken() == null || request.getRefreshToken().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Invalid request"));
        }

        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in db!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logoutRequest) {

        refreshTokenService.deleteByUserId(logoutRequest.getId());
        log.info("User logout attempt: {} ", logoutRequest.getId());

        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

}

