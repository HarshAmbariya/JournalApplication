package net.ambariya.journalApp.controllers;

import net.ambariya.journalApp.entity.User;
import net.ambariya.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAllUser(){
        List<User> all = userService.getAllJournalEntry();
        if(all!= null && !all.isEmpty()){
            return new  ResponseEntity<>(all, HttpStatus.OK);
        }
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("create-admin-user")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);
    }
}
