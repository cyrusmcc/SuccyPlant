package com.cm.contentmanagementapp.controllers;

import com.cm.contentmanagementapp.models.*;
import com.cm.contentmanagementapp.payload.request.*;
import com.cm.contentmanagementapp.payload.response.MessageResponse;
import com.cm.contentmanagementapp.security.jwt.JwtUtils;
import com.cm.contentmanagementapp.services.FileStorageService;
import com.cm.contentmanagementapp.services.MailService;
import com.cm.contentmanagementapp.services.UserService;
import com.cm.contentmanagementapp.services.resettoken.EmailResetTokenService;
import com.cm.contentmanagementapp.services.resettoken.PasswordResetTokenService;
import com.cm.contentmanagementapp.services.resettoken.ResetTokenService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/setting")
public class SettingsController {

    private UserService userService;

    private ResetTokenService resetTokenService;

    private PasswordResetTokenService passwordTokenService;

    private EmailResetTokenService emailTokenService;

    private MailService mailService;

    private FileStorageService fileStorageService;

    private JwtUtils utils;

    private static final Logger log = LoggerFactory.getLogger(SettingsController.class);


    @Autowired
    public SettingsController(UserService userService, ResetTokenService resetTokenService, PasswordResetTokenService passwordTokenService,
                              EmailResetTokenService emailTokenService, MailService mailService,
                              FileStorageService fileStorageService, JwtUtils utils) {
        this.userService = userService;
        this.resetTokenService = resetTokenService;
        this.passwordTokenService = passwordTokenService;
        this.emailTokenService = emailTokenService;
        this.mailService = mailService;
        this.fileStorageService = fileStorageService;
        this.utils = utils;
    }

    @PostMapping("/requestLostPasswordReset")
    public ResponseEntity<?> requestLostPasswordReset (@Valid @RequestBody LostPasswordResetRequest lostPasswordResetRequest,
                                                   HttpServletRequest request)
            throws NoSuchAlgorithmException {

        if (!userService.existsByEmail(lostPasswordResetRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("You will receive an email to reset your password if this email exists" +
                            " in our system."));
        }

        User user = userService.findByEmail(lostPasswordResetRequest.getEmail());

        // create new token, hash string token before storing, send email with unhashed token.
        PasswordResetToken passResetToken = new PasswordResetToken();
        String token = UUID.randomUUID().toString();
        passResetToken.setToken(resetTokenService.hashToken(token));
        passResetToken.setUser(user);
        passResetToken.setExpireDateByMinute(60);

        passwordTokenService.save(passResetToken);

        Mail mail = new Mail();
        mail.setFrom("defizzy@outlook.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        model.put("signature", "a signature here");
        // replace 3000 w/ request.getServerPort() when done testing;
        String url = request.getScheme() + "://" + request.getServerName() + ":" + 3000;
        model.put("resetUrl", url + "/reset-password-token-" + token);
        mail.setModel(model);
        mailService.sendEmail(mail);

        log.info("User requested a password reset");

        return ResponseEntity.ok(new MessageResponse("You will receive an email to reset your password if this email exists" +
                " in our system."));
    }

    @PostMapping("/requestSettingPasswordReset")
    public ResponseEntity<?> requestSettingPasswordReset(@Valid @RequestBody PasswordResetRequest passwordResetRequest,
                                                         HttpServletRequest request) {

        System.out.println(request.getHeader("Authorization"));
        utils.validateJwtToken(request.getHeader("Authorization"));

        /*
        if (!userService.existsById(passwordResetRequest.getId())) {
            log.info("No user found by id: {}", passwordResetRequest.getId());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("No user found by usename"));
        }

        if (passwordResetRequest.getCurrentPassword() == passwordResetRequest.getNewPassword()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Current and new password must be different."));
        }

        User user = userService.findById(passwordResetRequest.getId());

        if (!userService.isValidPassword(user, passwordResetRequest.getCurrentPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Invalid entry for current password"));
        }

        userService.updatePassword(user, passwordResetRequest.getNewPassword());
        userService.save(user);
        */
        return ResponseEntity.ok(new MessageResponse("Account password has been updated."));
    }

    @PostMapping("/handlePasswordReset")
    public ResponseEntity<?> handlePasswordReset(@Valid @RequestBody HandlePasswordResetRequest handlePassResetReq) {

        try {
            if (!resetTokenService.existsByToken(handlePassResetReq.getToken())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Invalid password reset token. Please try again"));
            }

            ResetToken token = resetTokenService.findByToken(handlePassResetReq.getToken());
            User user = token.getUser();
            userService.updatePassword(user, handlePassResetReq.getPassword());

            log.info("Handling user password reset");

            return ResponseEntity.ok(new MessageResponse("Password successfully changed."));

        } catch (NoSuchAlgorithmException nsae) {
            log.info("No such hash algo: {}", nsae);
        }

        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("handlePasswordReset hash error"));
    }

    @PostMapping("/changeEmailRequest")
    public ResponseEntity<?> changeEmailRequest(@Valid @RequestBody ChangeEmailRequest changeEmailRequest,
                                         HttpServletRequest request) throws NoSuchAlgorithmException {

        System.out.println(changeEmailRequest.getId() + " " + changeEmailRequest.getEmail() + " " + changeEmailRequest.getPassword());
        if (userService.existsByEmail(changeEmailRequest.getEmail())) {
            log.info("User attempted to change email but email already in use");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email already in use"));
        }

        User user = userService.findById(changeEmailRequest.getId());

        if (!userService.isValidPassword(user, changeEmailRequest.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid password, please try again"));
        }

        EmailResetToken emailResetToken = new EmailResetToken();
        String token = UUID.randomUUID().toString();
        emailResetToken.setToken(resetTokenService.hashToken(token));
        emailResetToken.setUser(user);
        emailResetToken.setExpireDateByHour(48);
        emailResetToken.setNewEmail(changeEmailRequest.getEmail());

        emailTokenService.save(emailResetToken);

        Mail mail = new Mail();
        mail.setFrom("defizzy@outlook.com");
        mail.setTo(changeEmailRequest.getEmail());
        mail.setSubject("Change email request");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        model.put("signature", "a signature here");
        // replace 3000 w/ request.getServerPort() when done testing;
        String url = request.getScheme() + "://" + request.getServerName() + ":" + 3000;
        model.put("resetUrl", url + "/email-change-confirmation-" + token);
        mail.setModel(model);
        mailService.sendEmail(mail);

        log.info("User requested an email change");

        return ResponseEntity.ok(new MessageResponse("A confirmation email will be sent to your new address. Click" +
                " the link provided link to update your email."));

    }

    @PostMapping("/handleEmailChange")
    public ResponseEntity<?> handleEmailChange(@Valid @RequestBody HandleEmailChangeRequest emailChangeRequest) {

        System.out.println(emailChangeRequest.getToken());

        try {
            if (!resetTokenService.existsByToken(emailChangeRequest.getToken())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Link is invalid or expired, submit a new email change request"));
            }

            ResetToken resetToken = resetTokenService.findByToken(emailChangeRequest.getToken());
            EmailResetToken emailResetToken = emailTokenService
                    .findByResetToken(resetToken)
                    .orElse(null);

            User user = resetToken.getUser();
            user.setEmail(emailResetToken.getNewEmail());
            userService.save(user);

            emailTokenService.delete(emailResetToken);

            log.info("User email updated");

        } catch (NoSuchAlgorithmException | NotFoundException e) {
            log.info("Exception: {}", e);
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error encountered, try again"));
        }

        return ResponseEntity.ok(new MessageResponse("Email successfully changed."));
    }

    @PostMapping(value = "/handleProfilePictureUpload")
    public ResponseEntity<?> handleProfilePictureUpload(@RequestParam("file") MultipartFile file) {

        Path root = Paths.get("uploads/profilePictures");

        try {
            String[] fileExtension = file.getOriginalFilename().split("\\.");
            String picId = UUID.randomUUID().toString();
            String path = "" + picId + "." + fileExtension[1];
            picId += "." + fileExtension[1];

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                System.out.println(currentUserName);
            }

            Files.copy(file.getInputStream(), root.resolve(picId));

            //fileStorageService.save(file);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(new MessageResponse("t"));

        /*
        //String[] fileExtension = pPCR.getFile().getName().split("\\.", 1);
        String path = "user-generated-images/profilepictures/";
        String picId = UUID.randomUUID().toString();
        //MultipartFile file = pPCR.getFile();
        File newNameFile;

        //picId.concat(".").concat(fileExtension[1]);
        path.concat(picId);

        //User user = userService.findById(pPCR.getId());

        System.out.println(sourceFile.getOriginalFilename());
        return ResponseEntity.ok(new MessageResponse("t"));

        newNameFile = new File(path);
        boolean flag = file.renameTo(newNameFile);

        if (flag) {
            System.out.println("set");
            user.setProfileImageId(picId);
            return ResponseEntity.ok(new MessageResponse("Profile picture changed"));
        }
        else {
            log.info("Failed to save file, image id already exists");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error encountered while uploading file, try again"));
        }
    */
    }

}
