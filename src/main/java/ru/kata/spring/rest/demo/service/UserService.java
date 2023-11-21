package ru.kata.spring.rest.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.rest.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findUserById(Long id);

    @Transactional
    void saveUser(User user);

    void update(User updateUser, Long id);

    User findByUsername(String username);

    void deleteUserById(long id);



}


