package pl.adamb.springapps.simplespringapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adamb.springapps.simplespringapp.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String firstName);

}
