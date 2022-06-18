package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    public User get(Long id) {
        return userDao.get(id);
    }

    @Transactional
    public void update(Long id, User user) {
        userDao.update(id, user);
    }

    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Transactional(readOnly = true)
    public List<User> list() {
        return userDao.list();
    }
}