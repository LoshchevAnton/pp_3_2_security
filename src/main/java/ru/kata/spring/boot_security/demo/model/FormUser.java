package ru.kata.spring.boot_security.demo.model;

import jakarta.persistence.Column;

public class FormUser {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String[] roles;

    public String getPassword() {
        if (password == null) {
            return null;
        }
        if (password.startsWith("{noop}")) {
            return password.substring("{noop}".length());
        } else {
            return password;
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
