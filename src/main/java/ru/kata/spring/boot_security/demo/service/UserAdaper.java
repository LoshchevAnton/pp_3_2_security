package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.FormUser;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

public interface UserAdaper {
    User convertFromFormUser(FormUser formUser, Set<Role> roles, String password);
}
