package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void addRole (Role role);
    Role findByNameRole (String name);
    List<Role> getAllRoles();
    Role findByIdRole(Long id);
    List<Role> listByRole(List<String> name);
    void add (User user);
    List<User> getAllUsers();
    void delete (Long id);
    void edit(User user);
    User getUser(Long id);
    User findByEmail (String email);
}
