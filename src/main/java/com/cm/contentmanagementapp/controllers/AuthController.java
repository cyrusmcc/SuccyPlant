package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.Mail;
import com.cm.contentmanagementapp.models.PasswordResetToken;
import com.cm.contentmanagementapp.models.RefreshToken;
import com.cm.contentmanagementapp.payload.request.*;
import com.cm.contentmanagementapp.repositories.PasswordResetTokenRepository;
import com.cm.contentmanagementapp.services.*;
import com.cm.contentmanagementapp.TokenRefreshException;
import com.cm.contentmanagementapp.payload.response.JwtResponse;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.payload.response.TokenRefreshResponse;
import com.cm.contentmanagementapp.security.jwt.JwtUtils;
import com.cm.contentmanagementapp.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authManager;

    private UserService userService;

    private RoleService roleService;

    private PasswordEncoder encoder;

    private JwtUtils jwtUtils;

    private RefreshTokenService refreshTokenService;

    private PasswordResetTokenService passResetTokenService;

    private MailService mailService;

    @Autowired
    public AuthController(AuthenticationManager authManager, UserService userService, RoleService roleService,
                          PasswordEncoder encoder, JwtUtils utils, RefreshTokenService refreshTokenService,
                          PasswordResetTokenService passResetTokenService, MailService mailService) {
        this.authManager = authManager;
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
        this.jwtUtils = utils;
        this.refreshTokenService = refreshTokenService;
        this.passResetTokenService = passResetTokenService;
        this.mailService = mailService;
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

    @PostMapping("/resetPasswordRequest")
    public ResponseEntity<?> requestPasswordReset (@Valid @RequestBody PasswordResetRequest passwordResetRequest,
                                             HttpServletRequest request)
            throws NoSuchAlgorithmException {

        if (!userService.existsByEmail(passwordResetRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("You will receive an email to reset your password if this email exists" +
                            " in our system."));
        }

        User user = userService.findByEmail(passwordResetRequest.getEmail());

        // create new token, hash string token before storing, send email with unhashed token.
        PasswordResetToken passResetToken = new PasswordResetToken();
        String token = UUID.randomUUID().toString();

        passResetToken.setToken(passResetTokenService.hashToken(token));
        passResetToken.setUser(user);
        passResetToken.setExpireDate(60);
        passResetTokenService.save(passResetToken);

        Mail mail = new Mail();
        mail.setFrom("defizzy@outlook.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        model.put("signature", "a signature here");
                                                                                  // request.getServerPort();
        String url = request.getScheme() + "://" + request.getServerName() + ":" + 3000;
        model.put("resetUrl", url + "/reset-password-token-" + token);
        mail.setModel(model);
        mailService.sendEmail(mail);

        log.info("User requested a password reset");


        return ResponseEntity.ok(new MessageResponse("You will receive an email to reset your password if this email exists" +
                " in our system."));
    }

    @PostMapping("/handlePasswordReset")
    public ResponseEntity<?> handlePasswordReset(@Valid @RequestBody HandlePasswordResetRequest handlePassResetReq) {

        try {
            if (!passResetTokenService.existsByToken(handlePassResetReq.getToken())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Invalid password reset token. Please try again"));
            }

            System.out.println(handlePassResetReq.getToken());
            PasswordResetToken token = passResetTokenService.findByToken(handlePassResetReq.getToken());
            User user = token.getUser();
            userService.updatePassword(user, token.getToken());

            log.info("Handling user password reset");

            return ResponseEntity.ok(new MessageResponse("Password successfully changed."));

        } catch (NoSuchAlgorithmException nsae) {
            log.info("No such hash algo: {}", nsae);
        }

        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("handlePasswordReset hash error"));
    }

    @PostMapping("/changeEmail")
    public ResponseEntity<?> changeEmail(@Valid @RequestBody ChangeEmailRequest changeEmailRequest) {

        if (userService.existsByEmail(changeEmailRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email already in use"));
        }

        User user = userService.findById(changeEmailRequest.getId());

        user.setEmail(changeEmailRequest.getEmail());
        userService .save(user);

        log.info("User email change attempt: {} ", user.getId());

        return ResponseEntity.ok(new MessageResponse("Email changed"));

    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
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

