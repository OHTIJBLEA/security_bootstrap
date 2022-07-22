package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class Admin {
    private final RoleService roleService;
    private final UserService userService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping(value = "/admin")
    public String listUsers(Principal principal, Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("principal", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.gelAllRoles());
        return "admin";
    }

    @GetMapping("/user")
    public String getUserById(Principal principal, Model model) {
        model.addAttribute("user", userService.getUsernameByName(principal.getName()));
        model.addAttribute("principal", userService.loadUserByUsername(principal.getName()));
        return "user";
    }


    @PostMapping("admin/user/new")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("newUserRoles") String[] roles) {
        userService.getSetRole(user, roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @PatchMapping("/admin/user/edit")
    public String editUser(@ModelAttribute("user") User user, @RequestParam("allRoles[]") String[] roles) {
        userService.getSetRole(user, roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}