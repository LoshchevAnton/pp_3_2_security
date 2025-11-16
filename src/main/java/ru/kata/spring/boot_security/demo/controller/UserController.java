package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String currentUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String name = userDetails.getUsername();
        User user = userService.getUserByName(name);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", false);
        model.addAttribute("roleNames", user.getRoleNames());
        return "user";
    }

    @GetMapping("/admin/users")
    public String getAllUsers(@RequestParam(value = "id", required = false) Integer id, Model model) {
        model.addAttribute("roles", roleService.getAll());
        if (id == null) {
            List<User> users = userService.getAll();
            model.addAttribute("users", users);
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
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("isNew", true);
        model.addAttribute("isAdmin", true);
        model.addAttribute("roleNames", new ArrayList<>());
        return "user";
    }

    @PostMapping("/admin/create")
    public String add(@ModelAttribute User user) {
        userService.add(user);
        return "redirect:/users?id=" + user.getId();
    }
    @PostMapping("/admin/update")
    public String update(@RequestParam Integer id, @ModelAttribute User user) {
        userService.update(user);
        return "redirect:/users?id=" + user.getId();
    }
    @PostMapping("/admin/delete")
    public String delete(@RequestParam Integer id, @ModelAttribute User user) {
        userService.delete(user);
        return "redirect:/users";
    }
}
