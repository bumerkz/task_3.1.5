package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AdminRestController {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;


    public AdminRestController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/allRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
    @GetMapping("/myPrincipal")
    public ResponseEntity<User> getPrincipal (Principal principal) {
        return new ResponseEntity<>(userService.findByEmail(principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/makeUser")
    public ResponseEntity<User> creatRestUser (@RequestBody User user) {
        List<String> list1 = user.getRoles().stream().map(role -> role.getRole()).collect(Collectors.toList());
        List<Role> list2 = roleService.listByRole(list1);
        user.setRoles(Set.copyOf(list2));
        userService.add(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/changeUser")
    public ResponseEntity<User> updateRestUser(@RequestBody User user) {
        List<String> list1 = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());
        List<Role> list2 = roleService.listByRole(list1);
        user.setRoles(Set.copyOf(list2));// здесь была строка  user.setRoles(Set.copyOf(list2))
        userService.edit(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteRestUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
