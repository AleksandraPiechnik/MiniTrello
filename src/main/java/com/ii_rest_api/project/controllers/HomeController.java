package com.ii_rest_api.project.controllers;

import com.ii_rest_api.project.db.model.RegisterForm;
import com.ii_rest_api.project.db.model.User;
import com.ii_rest_api.project.db.repositories.UserRepository;
import com.ii_rest_api.project.exceptions.EmptyInputException;
import com.ii_rest_api.project.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HomeController {

    private PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value = "register/user", method = RequestMethod.POST)
    public User addUser(@RequestBody RegisterForm registerForm) throws EmptyInputException, UserAlreadyExistsException {
        if(registerForm.getUsername()==""||registerForm.getPassword()==""||registerForm.getUsername()==null||registerForm.getPassword()==null){
            throw new EmptyInputException("Empty input occured!");
        }
        User existedUser=userRepository.findByUsername(registerForm.getUsername());
        if(existedUser!=null){
            throw new UserAlreadyExistsException("This username is already taken");
        }

        User user=new User(registerForm.getUsername(),passwordEncoder.encode(registerForm.getPassword()),"USER","USER");
        return userRepository.save(user);
    }


}

