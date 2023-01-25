package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String sawUsers (Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("messages", user);
        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roleUser", roleService.getAllRoles());
        return "admin";
    }
    @GetMapping(value = "new_user")
    public String makeUser(User user, Model model) {
        model.addAttribute("roleUser", roleService.getAllRoles());
        return "newUser";
    }
    @PostMapping("new_user")
    public String addUser(User user) {
        List<String> list1 = user.getRoles().stream().map(role -> role.getRole()).collect(Collectors.toList());
        List<Role> list2 = roleService.listByRole(list1);
        user.setRoles(Set.copyOf(list2));
        userService.add(user);
        return "redirect:/admin";
    }
    @GetMapping(value = "change/{id}")
    public String changeUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roleUser", roleService.getAllRoles());
        return "change";
    }
    @PostMapping(value = "/change")
    public String updateUser(User user) {
        List<String> list1 = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());
        List<Role> list2 = roleService.listByRole(list1);
        user.setRoles(Set.copyOf(list2));// здесь была строка  user.setRoles(Set.copyOf(list2))
        userService.edit(user);
        userService.edit(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "delete/{id}")
    public String delete(@PathVariable ("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
