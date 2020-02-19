package com.ii_rest_api.project.controllers;

import com.ii_rest_api.project.db.model.User;
import com.ii_rest_api.project.exceptions.EmptyInputException;
import com.ii_rest_api.project.exceptions.UserNotFoundException;
import com.ii_rest_api.project.db.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Integer user_id) throws UserNotFoundException, EmptyInputException {
        if (user_id == null) throw new EmptyInputException("Empty user ID input");
        if (!userRepository.existsById(user_id))
            throw new UserNotFoundException("There is no user with " + user_id + " user ID");
        User user = userRepository.findById(user_id).get();
        return user;
    }

    @RequestMapping(value = "/user/{user_id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable Integer user_id) throws UserNotFoundException, EmptyInputException {
        if (user_id == null) throw new EmptyInputException("Empty user ID input");
        if (!userRepository.existsById(user_id))
            throw new UserNotFoundException("There is no user with " + user_id + " user ID");
        userRepository.deleteById(user_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}