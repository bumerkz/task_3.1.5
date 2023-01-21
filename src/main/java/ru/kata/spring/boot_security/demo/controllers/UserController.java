package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.entity.User;

import java.security.Principal;

@Controller
public class UserController {
    private final UserDaoImpl userDao;

    public UserController(UserDaoImpl userDao) {
        this.userDao = userDao;
    }
    @GetMapping(value = "/user")
    public String userPage (ModelMap modelMap, Principal principal) {
        User user1 = userDao.findByEmail(principal.getName());
        modelMap.addAttribute("messages", user1);
        return "user";
    }
}
