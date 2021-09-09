package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class ResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expireDate;

    public ResetToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setExpireDateByMinute(int minutes) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutes);
        this.expireDate = now.getTime();
    }

    public void setExpireDateByHour(int hour) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, hour);
        this.expireDate = now.getTime();
    }

    public boolean isExpired() {
        return new Date().after(this.expireDate);
    }

}
