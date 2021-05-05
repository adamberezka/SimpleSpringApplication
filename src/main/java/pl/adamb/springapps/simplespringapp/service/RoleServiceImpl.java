package pl.adamb.springapps.simplespringapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamb.springapps.simplespringapp.entity.Role;
import pl.adamb.springapps.simplespringapp.repository.RoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class RoleServiceImpl implements RoleService{

    final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    public Set<Role> findAll() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public Optional<Role> findById(Integer theId) {
        return roleRepository.findById(theId);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteById(Integer theId) {
        roleRepository.deleteById(theId);
    }
}
