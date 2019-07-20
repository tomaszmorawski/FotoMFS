package com.example.fotomfs.Services;

import com.example.fotomfs.Model.Role;
import com.example.fotomfs.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final
    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void removeAllRoles() {
        roleRepository.deleteAll();
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.getByName(roleName);
    }
}
