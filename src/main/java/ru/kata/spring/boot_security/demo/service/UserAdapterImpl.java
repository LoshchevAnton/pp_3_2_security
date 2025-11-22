package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.FormUser;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

@Component
public class UserAdapterImpl implements UserAdaper{

    @Override
    public User convertFromFormUser(FormUser formUser, Set<Role> roles, String password) {
        User user = new User(formUser.getName(),  formUser.getEmail(), password);
        user.setId(formUser.getId());
        if (formUser.getRoles() != null) {
            user.setRoles(roles);
        }
        return user;
    }
}
