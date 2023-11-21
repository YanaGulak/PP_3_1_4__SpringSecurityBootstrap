package ru.kata.spring.rest.demo.service;

import ru.kata.spring.rest.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> findAll();

    Role findRoleById(Long id);

    Set<Role> findSetRoleBySetId(Set<Long> roles);
}
