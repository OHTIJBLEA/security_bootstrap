package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> findAllUsers();
    void deleteUserById(Long id);
    UserDetails loadUserByUsername(String username);
    User findUserById(Long userId);
    boolean saveUser(User user);
    boolean updateUser(User user);
    Long getUsernameByName(String name);
    boolean saveUserTest(User user);
    Set<Role> getSetRole(User user, String[] roles);
    void addTestUsers();
}
