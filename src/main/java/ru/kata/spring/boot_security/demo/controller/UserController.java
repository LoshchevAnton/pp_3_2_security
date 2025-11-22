package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserAdaper;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.stream.Collectors;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public UserController(UserService userService, RoleService roleService, UserAdaper userAdaper) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String currentUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String name = userDetails.getUsername();
        User user = userService.getUserByName(name);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", false);
        model.addAttribute("isNew", false);
        model.addAttribute("roles", roleService.getAll().stream().map(r -> r.getAuthority()).collect(Collectors.toList()));
        model.addAttribute("roleNames", user.getRoleNames());
        return "user";
    }


}
