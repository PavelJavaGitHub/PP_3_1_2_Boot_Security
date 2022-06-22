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
@RequestMapping("/user")
public class UserController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsServiceImpl, PasswordEncoder passwordEncoder) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String user(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "bootstrap/user";
    }

    @GetMapping("/{id}")
    public String userByID(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userDetailsServiceImpl.get(id));
        return "bootstrap/user";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userDetailsServiceImpl.get(id));
        model.addAttribute("allRoles", Role.values());
        return "bootstrap/creationForm";
    }

    @PostMapping("/{id}/edit")
    public String userSave(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetailsServiceImpl.save(user);
        return "redirect:/admin/list";
    }

    @PostMapping("/{id}/delete")
    public String delete (@PathVariable("id") long id) {
        userDetailsServiceImpl.delete(id);
        return "redirect:/admin/list";
    }
}
