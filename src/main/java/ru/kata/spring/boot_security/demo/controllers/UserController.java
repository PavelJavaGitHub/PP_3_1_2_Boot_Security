package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("user/{id}")
    public String userByID(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userDao.get(id));
        return "user";
    }

    @GetMapping("/admin/list")
    public String list(Model model) {
        model.addAttribute("users", userDao.list());
        return "list";
    }

    @GetMapping("/admin/create")
    public String createForm(@ModelAttribute("user") User user) {
        return "create";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user) {
        userDao.save(user);
        return "redirect:/admin/list";
    }

    @GetMapping("/user/{id}/edit")
    public String editForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userDao.get(id));
        model.addAttribute("allRoles", Role.values());
        return "edit";
    }

//    @PostMapping("/user/{id}/edit")
//    public String edit(@ModelAttribute("user") User user, @RequestParam Map<String, String> form, @PathVariable("id") long id) {
//
//        userDao.update(id, user);
//        return "redirect:/admin/list";
//    }

    @PostMapping("/user/{id}/edit")
    public String userSave(
            @ModelAttribute("user") User user,
            @RequestParam Map<String, String> form,
            @PathVariable("id") long id
    ) {

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        System.out.println(user);
        userDao.update(id, user);

        return "redirect:/user";
    }

//    @GetMapping("/user/{id}/edit")
//    public String editForm(@PathVariable("id") long id, Model model) {
//        model.addAttribute("user", userDao.get(id));
//        return "edit";
//    }
//
//    @PostMapping("/user/{id}/edit")
//    public String edit(@ModelAttribute("user") User user, @PathVariable("id") long id) {
//        userDao.update(id, user);
//        return "redirect:/admin/list";
//    }

    @PostMapping("user/{id}/delete")
    public String delete (@PathVariable("id") long id) {
        userDao.delete(id);
        return "redirect:/admin/list";
    }
}
