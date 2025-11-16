package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public User add(User user) {
        return userDao.save(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        Optional<User> optionalUser = userDao.findById(user.getId());
        if (optionalUser.isPresent()) {
            userDao.save(user);
        }
    }

    @Transactional
    @Override
    public void delete(User user) {
        Optional<User> optionalUser = userDao.findById(user.getId());
        if (optionalUser.isPresent()) {
            userDao.delete(user);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(Integer id) {
        Optional<User> optionalUser = userDao.findById(id);
        return optionalUser.orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByName(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
