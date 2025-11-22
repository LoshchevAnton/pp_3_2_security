package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.FormUser;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserAdaper;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserAdaper userAdaper;
    private final PasswordEncoder encoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserAdaper userAdaper, PasswordEncoder encoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.userAdaper = userAdaper;
        this.encoder = encoder;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("roles", roleService.getAll().stream().map(r -> r.getAuthority()).collect(Collectors.toList()));
        List<User> users = userService.getAll();
        model.addAttribute("users", users.toArray());
        return "users";
    }

    @GetMapping("/user")
    public String getUserById(@RequestParam(value = "id") Integer id, Model model) {
        model.addAttribute("roles", roleService.getAll().stream().map(r -> r.getAuthority()).collect(Collectors.toList()));
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleNames", user.getRoleNames());
        model.addAttribute("isNew", false);
        model.addAttribute("isAdmin", true);
        return "user";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("roles", roleService.getAll().stream().map(r -> r.getAuthority()).collect(Collectors.toList()));
        model.addAttribute("isNew", true);
        model.addAttribute("isAdmin", true);
        model.addAttribute("roleNames", new ArrayList<>());
        return "user";
    }

    @PostMapping("/create")
    public String add(@ModelAttribute FormUser formUser) {
        List<String> formUserRoles = formUser.getRoles();
        Set<Role> roles = roleService.getByNames(formUserRoles);
        User user = userAdaper.convertFromFormUser(formUser, roles, encoder.encode(formUser.getPassword()));
        userService.add(user);
        return "redirect:/admin/user?id=" + user.getId();
    }

    @PostMapping("/update")
    public String update(@ModelAttribute FormUser formUser) {
        List<String> formUserRoles = formUser.getRoles();
        Set<Role> roles = roleService.getByNames(formUserRoles);
        User user = userAdaper.convertFromFormUser(formUser, roles, encoder.encode(formUser.getPassword()));
        userService.update(user);
        return "redirect:/admin/user?id=" + user.getId();
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute FormUser formUser) {
        List<String> formUserRoles = formUser.getRoles();
        Set<Role> roles = roleService.getByNames(formUserRoles);
        User user = userAdaper.convertFromFormUser(formUser, roles, encoder.encode(formUser.getPassword()));
        userService.delete(user);
        return "redirect:/admin/users";
    }
}
