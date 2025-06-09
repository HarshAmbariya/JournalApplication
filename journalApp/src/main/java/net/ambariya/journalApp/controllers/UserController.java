package net.ambariya.journalApp.controllers;

import net.ambariya.journalApp.entity.User;
import net.ambariya.journalApp.repository.UserRepository;
import net.ambariya.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        Authentication Authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = Authentication.getName();
        User userInDb = userService.findByUserName(username);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.saveEntry(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication Authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(Authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

