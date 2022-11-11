package com.group10.softwareengineeringmetrics.controller;

import com.group10.softwareengineeringmetrics.models.User;
import com.group10.softwareengineeringmetrics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "https://localhost:8081")
@RestController
@RequestMapping("/db")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/list")
    public ResponseEntity<List<User>> listUsers(){
        List<User> users = new ArrayList<>(userRepository.findAll());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User newUser = userRepository.save(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/addMany")
    public ResponseEntity<List<User>> addUsers(@RequestBody List<User> users) {
        List<User> newUsers = userRepository.saveAll(users);
        return new ResponseEntity<>(newUsers, HttpStatus.CREATED);
    }

}
