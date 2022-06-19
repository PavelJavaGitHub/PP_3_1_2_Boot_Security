package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {

    void save(User user);

    User get(long id);

    User getByEmail(String email);

    void update(long id, User user);

    void delete(long id);

    List<User> list();
}
