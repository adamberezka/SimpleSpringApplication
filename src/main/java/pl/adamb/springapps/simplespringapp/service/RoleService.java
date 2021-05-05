package pl.adamb.springapps.simplespringapp.service;

import pl.adamb.springapps.simplespringapp.entity.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleService {

    public Role findRoleByRoleName(String roleName);

    public Set<Role> findAll();

    public Optional<Role> findById(Integer theId);

    public void save(Role role);

    public void deleteById(Integer theId);

}
