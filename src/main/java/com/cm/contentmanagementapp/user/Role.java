package com.cm.contentmanagementapp.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter @Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private EnumRole name;

    public Role(EnumRole name) {
        this.name = name;
    }

    public EnumRole getName() {
        return name;
    }
}
