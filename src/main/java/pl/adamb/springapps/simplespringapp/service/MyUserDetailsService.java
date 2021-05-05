package pl.adamb.springapps.simplespringapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.adamb.springapps.simplespringapp.entity.MyUserDetails;
import pl.adamb.springapps.simplespringapp.entity.User;
import pl.adamb.springapps.simplespringapp.repository.UserRepository;

import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(s);

        if(user.isPresent()) {
            User theUser = user.get();
            return new MyUserDetails(theUser);
        }else{
            throw new UsernameNotFoundException("Could not find user: " + s);
        }
    }
}
