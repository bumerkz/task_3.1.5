package ru.kata.spring.boot_security.demo.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class UtilDB {
    //@Autowired
    final private UserServiceImpl userService;
    final private RoleServiceImpl roleService;

    public UtilDB(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void utilDB() {
        Set<Role> roleAdmin = new HashSet<>();
        Set<Role> roleUser = new HashSet<>();
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        roleService.addRole(role1);
        roleService.addRole(role2);
        roleAdmin.add(role1);
        roleAdmin.add(role2);
        roleUser.add(role2);
        User user1 = new User("SergeyKor", 37, "admin@ya.ru","admin", roleAdmin);
        User user2 = new User("User", 37, "user@ya.ru","user", roleUser);
        userService.add(user1);
        userService.add(user2);
    }
}
