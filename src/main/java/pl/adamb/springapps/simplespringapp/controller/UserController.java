package pl.adamb.springapps.simplespringapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.adamb.springapps.simplespringapp.entity.Role;
import pl.adamb.springapps.simplespringapp.entity.User;
import pl.adamb.springapps.simplespringapp.service.RoleService;
import pl.adamb.springapps.simplespringapp.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    @GetMapping("/register")
    public String registerForm(Model model){

        model.addAttribute("user", new User());

        return "registerPage";

    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User newUser, Model model){

        Optional<User> user = userService.findUserByUsername(newUser.getUsername());
        if(user.isPresent()){
            model.addAttribute("user", newUser);
            model.addAttribute("error", "User already exists");
            return "registerPage";
        }else{
            Role role = roleService.findRoleByRoleName("ROLE_USER");
            Set<Role> roles = new HashSet<Role>();
            roles.add(role);
            newUser.setRolesList(roles);
            userService.saveUser(newUser);

            return "redirect:login";
        }
    }

    @RequestMapping("/users")
    public String getAllUsers(Model model){

        Set<User> users = userService.findAll();

        model.addAttribute("users", users);

        return "users";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(Model model, @RequestParam Integer userId){

        userService.deleteById(userId);

        Set<User> users = userService.findAll();

        model.addAttribute("users", users);

        return "redirect:users";
    }

    @RequestMapping("/showAddUserForm")
    public String addUserForm(Model model){

        model.addAttribute("user", new User());

        return "addUserForm";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User theUser, Model model){

        Role role = roleService.findRoleByRoleName("ROLE_USER");
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        theUser.setRolesList(roles);

        userService.saveUser(theUser);

        Set<User> users = userService.findAll();

        model.addAttribute("users", users);

        return "users";
    }

    @RequestMapping("/loggedOut")
    public String loggedOut(){

        return "loggedOut";
    }

}
