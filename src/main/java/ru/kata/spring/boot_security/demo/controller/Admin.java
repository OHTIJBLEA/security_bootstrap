package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class Admin {
    private final RoleService roleService;
    private final UserService userService;
    private final RoleRepository roleRepository;

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

    @PostConstruct
    public void addTestUsers() {
        roleRepository.save(new Role(1L, "ROLE_ADMIN"));
        roleRepository.save(new Role(2L, "ROLE_USER"));
        User newAdmin = new User("admin", "admin", "admin", (byte) 18, "admin", roleService.getListRoleByName(new String[]{"ROLE_ADMIN"}));
        userService.saveUserTest(newAdmin);
        User newUser = new User("user", "user", "user", (byte) 18, "user", roleService.getListRoleByName(new String[]{"ROLE_USER"}));
        userService.saveUserTest(newUser);
    }
}