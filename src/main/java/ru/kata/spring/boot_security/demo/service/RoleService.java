package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;

public interface RoleService {
    void addRole (Role role);
    Role findByNameRole (String name);
    List<Role> getAllRoles();
    Role findByIdRole(Long id);
    List<Role> listByRole(List<String> name);
}
