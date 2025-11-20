package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.FormUser;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Component
public class UserAdapterImpl implements UserAdaper{
    private final RoleService roleService;
    public UserAdapterImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public User convertFromFormUser(FormUser formUser) {
        User user = new User(formUser.getName(),  formUser.getEmail(), "{noop}" + formUser.getPassword());
        user.setId(formUser.getId());
        if (formUser.getRoles() != null) {
            List<Role> roles = roleService.getByNames(formUser.getRoles());
            user.setRoles(roles);
        }
        return user;
    }
}
