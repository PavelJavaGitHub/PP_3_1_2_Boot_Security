package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserDetailsServiceImpl userDetailsServiceImpl, PasswordEncoder passwordEncoder) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String admin(@AuthenticationPrincipal User user, @ModelAttribute("createdUser") User createdUser, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("createdUser", createdUser);
        model.addAttribute("users", userDetailsServiceImpl.list());
        model.addAttribute("allRoles", Role.values());
        return "bootstrap/admin";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetailsServiceImpl.save(user);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userDetailsServiceImpl.get(id));
        model.addAttribute("allRoles", Role.values());
        return "bootstrap/admin";
    }

    @PostMapping("/edit")
    public String userSave(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetailsServiceImpl.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete (@ModelAttribute("user") User user) {
        userDetailsServiceImpl.delete(user.getId());
        return "redirect:/admin";
    }
}
