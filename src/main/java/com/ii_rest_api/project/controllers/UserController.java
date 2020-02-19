package com.ii_rest_api.project.controllers;

import com.ii_rest_api.project.db.model.*;
import com.ii_rest_api.project.db.repositories.*;
import com.ii_rest_api.project.exceptions.*;
import com.ii_rest_api.project.utils.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private WallRepository wallRepository;

    private ExceptionHandler exceptionHandler = new ExceptionHandler();


    @RequestMapping(value = "/{user_id}/team", method = RequestMethod.POST)
    public Team createTeam(@PathVariable Integer user_id) throws UserNotFoundException, NotAuthorizedUserException, EmptyInputException {
        if (user_id == null) throw new EmptyInputException("Empty user ID input");
        if (!userRepository.existsById(user_id))
            throw new UserNotFoundException("There is no user with " + user_id + " user ID");
        exceptionHandler.isUnAuthorizedUserException("Given user id is not compatible with user principal ID", user_id, userRepository);

        User user = userRepository.findById(user_id).get();
        Wall wall = new Wall();
        user.getWallSet().add(wall);
        userRepository.save(user);
        Team team = new Team(user, wall);
        return teamRepository.save(team);
    }

    @RequestMapping(value = "/team", method = RequestMethod.PUT)
    public Team addUser(@RequestBody MemberForm memberForm) throws NotAuthorizedUserException, TeamNotFoundException, UserNotFoundException, EmptyInputException {
        exceptionHandler.isMemberFormEmpty(memberForm);
        if (!teamRepository.existsById(memberForm.getTeam_id()))
            throw new TeamNotFoundException("There is no team with " + memberForm.getMember_id() + " team ID");
        if (!userRepository.existsById(memberForm.getMember_id()))
            throw new UserNotFoundException("There is no user with " + memberForm.getMember_id() + " member ID");

        Team team = teamRepository.findById(memberForm.getTeam_id()).get();
        exceptionHandler.isUnAuthorizedUserToTeamException("User has to be in team in order to add new member", team, userRepository);
        User user = userRepository.findById(memberForm.getMember_id()).get();
        team.getUserSet().add(user);
        user.getWallSet().add(team.getWall());
        userRepository.save(user);
        return teamRepository.save(team);
    }

    @RequestMapping(value = "/{user_id}/team", method = RequestMethod.GET)
    public Set<Team> getTeam(@PathVariable Integer user_id) throws UserNotFoundException, NotAuthorizedUserException, EmptyInputException {
        if (user_id == null) throw new EmptyInputException("Empty user ID input");
        if (!userRepository.existsById(user_id))
            throw new UserNotFoundException("There is no user with " + user_id + " user ID");
        exceptionHandler.isUnAuthorizedUserException("Given user id is not compatible with user principal ID", user_id, userRepository);
        User user = userRepository.findById(user_id).get();

        return user.getTeamSet();
    }

    @RequestMapping(value = "/{user_id}/wall", method = RequestMethod.POST)
    public Wall addWall(@PathVariable Integer user_id) throws NotAuthorizedUserException, UserNotFoundException, EmptyInputException {
        if (user_id == null) throw new EmptyInputException("Empty user ID input");
        if (!userRepository.existsById(user_id))
            throw new UserNotFoundException("There is no user with " + user_id + " user ID");
        exceptionHandler.isUnAuthorizedUserException("Given user id is not compatible with user principal ID", user_id, userRepository);
        User user = userRepository.findById(user_id).get();
        Wall wall = new Wall();
        wall.getUserSet().add(user);
        wallRepository.save(wall);

        return wall;
    }

    @RequestMapping(value = "/{user_id}/wall", method = RequestMethod.GET)
    public Set<Wall> getWalls(@PathVariable Integer user_id) throws UserNotFoundException, NotAuthorizedUserException, EmptyInputException {
        if (user_id == null) throw new EmptyInputException("Empty user ID input");
        if (!userRepository.existsById(user_id))
            throw new UserNotFoundException("There is no user with " + user_id + " user ID");
        exceptionHandler.isUnAuthorizedUserException("Given user id is not compatible with user principal ID", user_id, userRepository);
        User user = userRepository.findById(user_id).get();

        return user.getWallSet();
    }

    @RequestMapping(value = "/{user_id}/board", method = RequestMethod.POST)
    public Board addBoard(@PathVariable Integer user_id, @RequestBody WallForm wallForm) throws NotAuthorizedUserException, UserNotFoundException, WallNotFoundException, EmptyInputException {
        if (user_id == null) throw new EmptyInputException("Empty user ID input");
        exceptionHandler.isWallFormEmpty(wallForm);
        if (!userRepository.existsById(user_id))
            throw new UserNotFoundException("There is no user with " + user_id + " user ID");
        exceptionHandler.isUnAuthorizedUserException("Given user id is not compatible with user principal ID", user_id, userRepository);

        if (!wallRepository.existsById(wallForm.getWall_id()))
            throw new WallNotFoundException("There is no wall with " + wallForm.getWall_id() + " wall ID");
        exceptionHandler.isUnAuthorizedUserToWallException("Given wall doesnt belong to this user", user_id, wallForm.getWall_id(), userRepository);

        Wall wall = wallRepository.findById(wallForm.getWall_id()).get();
        Board board = new Board();
        board.setWall(wall);
        boardRepository.save(board);
        return board;
    }

    @RequestMapping(value = "/{user_id}/task", method = RequestMethod.POST)
    public Task addTask(@PathVariable Integer user_id, @RequestBody TaskForm taskForm) throws UserNotFoundException, NotAuthorizedUserException, BoardNotFoundException, NotAuthorizedUserToBoardException, EmptyInputException {
        if (user_id == null) throw new EmptyInputException("Empty user ID input");
        exceptionHandler.isTaskFormEmpty(taskForm);
        if (!userRepository.existsById(user_id))
            throw new UserNotFoundException("There is no user with " + user_id + " user ID");
        exceptionHandler.isUnAuthorizedUserException("Given user id is not compatible with user principal ID", user_id, userRepository);

        Integer board_id = taskForm.getBoard_id();
        if (!boardRepository.existsById(board_id))
            throw new BoardNotFoundException("There is no board with " + board_id + " board ID");
        exceptionHandler.isUnAuthorizedUserToBoardException("Given board doesnt belong to this user", user_id, board_id, userRepository);

        String description = taskForm.getDescription();
        String label = taskForm.getLabelName();
        Board board = boardRepository.findById(board_id).get();
        Task newTask = new Task(label, description, board);
        taskRepository.save(newTask);
        return newTask;
    }

    @RequestMapping(value = "/{user_id}/task", method = RequestMethod.GET)
    public Set<Task> getTasks(@PathVariable Integer user_id) throws UserNotFoundException, NotAuthorizedUserException, EmptyInputException {
        if (user_id == null) throw new EmptyInputException("Empty user ID input");
        if (!userRepository.existsById(user_id))
            throw new UserNotFoundException("There is no user with " + user_id + " user ID");
        exceptionHandler.isUnAuthorizedUserException("Given user id is not compatible with user principal ID", user_id, userRepository);

        User user = userRepository.findById(user_id).get();
        Set<Task> taskSet = new HashSet<>();
        for (Wall wall : user.getWallSet()) {
            for (Board board : wall.getBoards()) {
                for (Task task : board.getTaskSet()) {
                    taskSet.add(task);
                }
            }
        }


        return taskSet;
    }


}
