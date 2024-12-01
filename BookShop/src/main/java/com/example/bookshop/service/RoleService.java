package com.example.bookshop.service;

import com.example.bookshop.model.Role;
import com.example.bookshop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {this.roleRepository = roleRepository;}

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getAll(String roleName) {
        return roleRepository.findAll();
    }

    public Role getById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role update(Role role) {
        return roleRepository.save(role);
    }

    public void delete(int id) {
        roleRepository.deleteById(id);
    }
}
