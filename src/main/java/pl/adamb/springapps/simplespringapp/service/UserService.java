package pl.adamb.springapps.simplespringapp.service;

import pl.adamb.springapps.simplespringapp.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> findUserByUsername(String username);

    public Set<User> findAll();

    public User findById(Integer theId);

    public void save(User user);

    public void deleteById(Integer theId);

    void saveUser(User user);
}
