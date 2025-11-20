package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.FormUser;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserAdaper;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserAdaper userAdaper;

    @Autowired
    public UserController(UserService userService, RoleService roleService, UserAdaper userAdaper) {
        this.userService = userService;
        this.roleService = roleService;
        this.userAdaper = userAdaper;
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

    @GetMapping("/admin/users")
    public String getAllUsers(@RequestParam(value = "id", required = false) Integer id, Model model) {
        model.addAttribute("roles", roleService.getAll().stream().map(r -> r.getAuthority()).collect(Collectors.toList()));
        if (id == null) {
            List<User> users = userService.getAll();
            model.addAttribute("users", users.toArray());
            return "users";
        } else {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            model.addAttribute("roleNames", user.getRoleNames());
            model.addAttribute("isNew", false);
            model.addAttribute("isAdmin", true);
            return "user";
        }
    }
    @GetMapping("/admin/create")
    public String getUserById(Model model) {
        model.addAttribute("roles", roleService.getAll().stream().map(r -> r.getAuthority()).collect(Collectors.toList()));
        model.addAttribute("isNew", true);
        model.addAttribute("isAdmin", true);
        model.addAttribute("roleNames", new ArrayList<>());
        return "user";
    }

    @PostMapping("/admin/create")
    public String add(@ModelAttribute FormUser formUser) {
        User user = userAdaper.convertFromFormUser(formUser);
        userService.add(user);
        return "redirect:/admin/users?id=" + user.getId();
    }
    @PostMapping("/admin/update")
    public String update(@RequestParam Integer id, @ModelAttribute FormUser formUser) {
        User user = userAdaper.convertFromFormUser(formUser);
        userService.update(user);
        return "redirect:/admin/users?id=" + user.getId();
    }
    @PostMapping("/admin/delete")
    public String delete(@RequestParam Integer id, @ModelAttribute FormUser formUser) {
        User user = userAdaper.convertFromFormUser(formUser);
        userService.delete(user);
        return "redirect:/admin/users";
    }
}
