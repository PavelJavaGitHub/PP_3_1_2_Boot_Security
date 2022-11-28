package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    @Autowired
    public AdminController(UserDetailsServiceImpl userDetailsServiceImpl, PasswordEncoder passwordEncoder, UserDao userDao) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @GetMapping
    public String admin() {
        return "admin";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userDetailsServiceImpl.list());
        return "list";
    }

    @GetMapping("/create")
    public String createForm(@ModelAttribute("user") User user, Model model) {
        System.out.println(userDao.getAllRoles());
        model.addAttribute("allRoles", userDao.getAllRoles());
        return "creationForm";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetailsServiceImpl.save(user);
        return "redirect:admin/list";
    }
}
