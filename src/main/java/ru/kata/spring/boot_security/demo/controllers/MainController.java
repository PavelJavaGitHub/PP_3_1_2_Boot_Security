package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

@Controller
public class MainController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MainController(UserDetailsServiceImpl userDetailsServiceImpl, PasswordEncoder passwordEncoder) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String login() {
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "bootstrap/user";
    }

    @GetMapping("user/{id}")
    public String userByID(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userDetailsServiceImpl.getUserById(id));
        return "bootstrap/user";
    }

    @RequestMapping("user/getOne")
    @ResponseBody
    public User getOneUser(long id) {
        return userDetailsServiceImpl.getUserById(id);
    }
}
