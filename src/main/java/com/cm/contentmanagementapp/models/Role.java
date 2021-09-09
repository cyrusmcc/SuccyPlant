package com.cm.contentmanagementapp.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
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

    public Role() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(EnumRole name) {
        this.name = name;
    }
}
