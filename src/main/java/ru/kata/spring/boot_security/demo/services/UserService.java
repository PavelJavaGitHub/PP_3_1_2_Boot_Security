package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    void save(User user);

    User get(Long id);

    User getByEmail(String email);

    void update(Long id, User user);

    void delete(Long id);

    List<User> list();
}
