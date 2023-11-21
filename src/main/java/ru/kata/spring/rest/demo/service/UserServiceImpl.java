package ru.kata.spring.rest.demo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.rest.demo.DAO.UserRepository;
import ru.kata.spring.rest.demo.model.Role;
import ru.kata.spring.rest.demo.model.User;
import ru.kata.spring.rest.demo.util.UserNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;


    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;

        this.roleService = roleService;
    }


    //из UserService
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        User userFromDB = userRepository.findUserById(id);
        if (userFromDB == null) {
            throw new UserNotFoundException(String.format("Пользователь с id = %d не найден", id));
        }
        return userFromDB;
    }



    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveUser(getUserWithRoles(user)); //работа с ролями - в контроллере
    }

    @Transactional
    @Override
    public void update(User updateUser, Long id) {
        User user = findUserById(id);
        user.setName(updateUser.getName());
        user.setLastName(updateUser.getLastName());
        user.setAge(updateUser.getAge());
        user.setEmail(updateUser.getEmail());
        user.setUsername(updateUser.getUsername());
        user.setRoles(updateUser.getRoles());
        if (!user.getPassword().equals(updateUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            userRepository.updateUser(user);
        }
        userRepository.updateUser(user);
    }

    @Override
    public User findByUsername(String username) {
        User userFromDB = userRepository.findByUsername(username);
        if (userFromDB == null) {
            throw new UsernameNotFoundException(String.format("Пользователь с username = %s не найден", username));
        }
        return userFromDB;
    }


    @Transactional
    @Override
    public void deleteUserById(long id) {
        userRepository.deleteUserById(id);
    }

    //служебный метод для загрузки ролей из RoleRepository, чтобы не создавались новые роли
    private User getUserWithRoles(User user) {
        var allRoles = roleService.findAll();
        var roles = user.getRoles();
        Set<Role> usersRoles = new HashSet<>();

        for (Role role : allRoles) {
            for (Role userRole : roles) {
                if (role.getRole().equals(userRole.getRole())) {
                    usersRoles.add(role);
                }
            }
        }
        user.setRoles(usersRoles);
        return user;
    }

}