package com.example.bookshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends AbstractBaseEntity{

    @OneToMany(mappedBy = "role")
    private List<User> users;

    @NotBlank
    private String name;

    public Role() {}

    public Role(List<User> users, String name) {
        this.users = users;
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
