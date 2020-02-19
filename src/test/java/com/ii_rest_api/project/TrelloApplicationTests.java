package com.ii_rest_api.project;

import com.ii_rest_api.project.controllers.AdminController;
import com.ii_rest_api.project.controllers.HomeController;
import com.ii_rest_api.project.controllers.ManagementController;
import com.ii_rest_api.project.controllers.UserController;
import com.ii_rest_api.project.db.model.RegisterForm;
import com.ii_rest_api.project.db.model.User;
import com.ii_rest_api.project.db.repositories.TeamRepository;
import com.ii_rest_api.project.db.repositories.UserRepository;
import com.ii_rest_api.project.db.repositories.WallRepository;
import com.ii_rest_api.project.exceptions.EmptyInputException;
import com.ii_rest_api.project.exceptions.UserAlreadyExistsException;
import com.ii_rest_api.project.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TrelloApplicationTests {

    @Autowired
    AdminController adminController;
    @Autowired
    UserController userController;
    @Autowired
    ManagementController managementController;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    WallRepository wallRepository;
    @Autowired
    HomeController homeController;


    @Test
    public void getUserTest() throws EmptyInputException, UserNotFoundException {
        User user = new User("name", "password", "USER", "USER");
        userRepository.save(user);
        Integer user_id = user.getUser_id();
        User userExample = adminController.getUser(user_id);
        assertEquals(user.getUser_id(), userExample.getUser_id());
        assertEquals(user.getUsername(), userExample.getUsername());
        assertEquals(user.getRoles(), userExample.getRoles());
        userRepository.delete(userRepository.findByUsername("name"));
    }

    @Test
    public void getUserWhenUserDoesntExistsTest() {
        User user = new User("name", "password", "USER", "USER");
        userRepository.save(user);
        Integer user_id = user.getUser_id();
        userRepository.delete(user);
        Assertions.assertThrows(UserNotFoundException.class, new Executable() {
            public void execute() throws Throwable {
                adminController.getUser(user_id);
            }
        });
    }

    @Test
    public void getUserWhenUserIsNullTest() throws EmptyInputException, UserNotFoundException {
        Assertions.assertThrows(EmptyInputException.class, new Executable() {
            public void execute() throws Throwable {
                adminController.getUser(null);
            }
        });
    }

    @Test
    public void deleteUserTest() throws EmptyInputException, UserNotFoundException {
        User user = new User("name", "password", "USER", "USER");
        userRepository.save(user);
        Integer user_id = user.getUser_id();
        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null), adminController.deleteUser(user_id));
        assertEquals(false, userRepository.existsById(user_id));
    }

    @Test
    public void deleteUserWhenUserIsNullTest() throws EmptyInputException, UserNotFoundException {
        Assertions.assertThrows(EmptyInputException.class, new Executable() {
            public void execute() throws Throwable {
                adminController.deleteUser(null);
            }
        });
    }

    @Test
    public void deleteUserWhenDoesntExistsTest() {
        User user = new User("name", "password", "USER", "USER");
        userRepository.save(user);
        Integer user_id = user.getUser_id();
        userRepository.delete(user);
        Assertions.assertThrows(UserNotFoundException.class, new Executable() {
            public void execute() throws Throwable {
                adminController.deleteUser(user_id);
            }
        });
    }

    @Test
    public void getUserExceptionUserNotFoundTest() throws EmptyInputException, UserNotFoundException {
        User user = new User("name", "password", "USER", "USER");
        userRepository.save(user);
        Integer user_id = user.getUser_id();
        userRepository.delete(user);

        Assertions.assertThrows(UserNotFoundException.class, () -> adminController.getUser(user_id));
    }

    @Test
    public void getUsersTest() {
        User user = new User("name", "password", "USER", "USER");
        userRepository.save(user);
        assertEquals(userRepository.findAll().toString(), adminController.getUsers().toString());
        userRepository.delete(userRepository.findByUsername("name"));
    }

    @Test
    public void getTeamsTest() {
        assertEquals(teamRepository.findAll().toString(), managementController.getTeams().toString());
    }

    @Test
    public void addUserTest() throws EmptyInputException, UserAlreadyExistsException {
        homeController.addUser(new RegisterForm("name", "pass"));
        assertEquals(true,userRepository.existsById(userRepository.findByUsername("name").getUser_id()));
        userRepository.delete(userRepository.findByUsername("name"));
    }

    @Test
    public void addUserWhenInputNameIsEmptyTest(){
        Assertions.assertThrows(EmptyInputException.class, new Executable() {
            public void execute() throws Throwable {
                homeController.addUser(new RegisterForm("", "pass"));
            }
        });
    }

    @Test
    public void addUserWhenInputPasswordIsEmptyTest()  {
        Assertions.assertThrows(EmptyInputException.class, new Executable() {
            public void execute() throws Throwable {
                homeController.addUser(new RegisterForm("name", ""));
            }
        });
    }

    @Test
    public void addUserWhenInputNameIsNullTest(){
        Assertions.assertThrows(EmptyInputException.class, new Executable() {
            public void execute() throws Throwable {
                homeController.addUser(new RegisterForm(null, "pass"));
            }
        });
    }

    @Test
    public void addUserWhenInputPasswordIsNullTest()  {
        Assertions.assertThrows(EmptyInputException.class, new Executable() {
            public void execute() throws Throwable {
                homeController.addUser(new RegisterForm("name", null));
            }
        });
    }

    @Test
    public void addUserWhenUsernameIsAlreadyTakenTest()  {
        User user = new User("name", "password", "USER", "USER");
        userRepository.save(user);

        Assertions.assertThrows(UserAlreadyExistsException.class, new Executable() {
            public void execute() throws Throwable {
                homeController.addUser(new RegisterForm("name", "password"));
            }
        });
        userRepository.delete(user);
    }
}
