package pl.adamb.springapps.simplespringapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adamb.springapps.simplespringapp.entity.Role;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleByRoleName(String roleName);

}
