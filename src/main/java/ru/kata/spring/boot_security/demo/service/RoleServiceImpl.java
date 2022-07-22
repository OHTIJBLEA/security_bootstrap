package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Set<Role> gelAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public Set<Role> getListRoleByName(String[] roleName) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roleName) {
            roleSet.add(roleRepository.findRoleByName(role));
        }
        return roleSet;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
