package com.epam.controllers;


import com.epam.dto.UserRequest;
import com.epam.dto.UserResponse;

import com.epam.exception.UserException;
import com.epam.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserApis {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) throws UserException {
        return  new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);

    }

    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getUsers()  {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") int userId) throws UserException {
         return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);

    }

    @GetMapping("username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable("username") String username) throws UserException {
        return  new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);

    }


    @PutMapping("{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") int userId ,@RequestBody @Valid UserRequest userRequest) throws UserException {
        userRequest.setUserId(userId);
        return new ResponseEntity<>(userService.updateUser(userRequest), HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("Delete request had been executed",HttpStatus.NO_CONTENT);
    }


}
