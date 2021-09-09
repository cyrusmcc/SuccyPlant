package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import java.util.Date;


@Entity
public class EmailResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String newEmail;

    @OneToOne(targetEntity = ResetToken.class, fetch = FetchType.EAGER, cascade=CascadeType.ALL , orphanRemoval=true)
    @JoinColumn(nullable = false, name = "reset_token_id")
    private ResetToken resetToken;

    public EmailResetToken() {
        this.resetToken = new ResetToken();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getToken() {
        return resetToken.getToken();
    }

    public void setToken(String token) {
        resetToken.setToken(token);
    }

    public User getUser() {
        return resetToken.getUser();
    }

    public void setUser(User user) {
        resetToken.setUser(user);
    }

    public Date getExpireDate() {
        return resetToken.getExpireDate();
    }

    public void setExpireDate(Date expireDate) {
        resetToken.setExpireDate(expireDate);
    }

    public void setExpireDateByMinute(int minute) {
        resetToken.setExpireDateByMinute(minute);
    }

    public void setExpireDateByHour(int hour) {
        resetToken.setExpireDateByHour(hour);
    }

    public ResetToken getResetToken() {
        return resetToken;
    }

    public void setResetToken(ResetToken resetToken) {
        this.resetToken = resetToken;
    }
}
