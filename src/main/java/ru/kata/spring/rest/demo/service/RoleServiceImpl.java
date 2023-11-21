package ru.kata.spring.rest.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.rest.demo.DAO.RoleRepository;
import ru.kata.spring.rest.demo.model.Role;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(Long id) {
        return roleRepository.findRoleById(id);
    }

    @Override
    public Set<Role> findSetRoleBySetId(Set<Long> roleIds) {
        return roleRepository.findSetRoleBySetId(roleIds);
    }
}

