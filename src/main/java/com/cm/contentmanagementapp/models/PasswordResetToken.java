package com.cm.contentmanagementapp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expireDate;


    public void setExpireDate(int minutes) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutes);
        this.expireDate = now.getTime();
    }

    public boolean isExpired() {
        return new Date().after(this.expireDate);
    }



    /*
    public PasswordResetToken(final String token) {
        this.token = token;
        this.expireDate = calculateExpireDate(EXPIRATION);
    }

    public passwordResetToken(final String token, final User user) {
        this.token = token;
        this.user = user;
        this.expireDate = calculateExpireDate(EXPIRATION);
    }

    private Date calculateExpireDate(final int )
    */
}
