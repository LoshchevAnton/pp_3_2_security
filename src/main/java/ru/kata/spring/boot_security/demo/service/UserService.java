package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User add(User user);
    void update(User user);
    void delete(User user);
    List<User> getAll();
    User getUserById(Integer id);
    User getUserByName(String name);
}
