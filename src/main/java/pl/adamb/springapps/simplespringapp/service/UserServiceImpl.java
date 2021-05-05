package pl.adamb.springapps.simplespringapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.adamb.springapps.simplespringapp.entity.Role;
import pl.adamb.springapps.simplespringapp.entity.User;
import pl.adamb.springapps.simplespringapp.repository.UserRepository;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Set<User> findAll() {
        return new HashSet<User>(userRepository.findAll());
    }

    @Override
    public User findById(Integer theId) {
        Optional<User> result = userRepository.findById(theId);

        User theUser = null;

        if(result.isPresent())
            theUser = result.get();
        else
            throw new RuntimeException("Did not find user by id=" + theId);

        return theUser;
    }

    @Override
    @Transactional
    public void save(User user) {

        userRepository.save(user);
    }

    @Override
    public void deleteById(Integer theId) {
        userRepository.deleteById(theId);
    }

    @Override
    public void saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }
}
