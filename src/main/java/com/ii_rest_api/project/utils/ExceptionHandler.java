package com.ii_rest_api.project.utils;

import com.ii_rest_api.project.db.model.*;
import com.ii_rest_api.project.db.repositories.UserRepository;
import com.ii_rest_api.project.exceptions.EmptyInputException;
import com.ii_rest_api.project.exceptions.NotAuthorizedToTaskException;
import com.ii_rest_api.project.exceptions.NotAuthorizedUserException;
import com.ii_rest_api.project.exceptions.NotAuthorizedUserToBoardException;
import org.springframework.security.core.context.SecurityContextHolder;

public class ExceptionHandler {


    public void isUnAuthorizedToTaskException(String message, Integer task_id, UserRepository userRepository) throws NotAuthorizedToTaskException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(principal.toString());
        boolean UserTaskFlag = false;
        for (Wall wall : user.getWallSet()) {
            for (Board board : wall.getBoards()) {
                for (Task task : board.getTaskSet()) {
                    if (task.getTask_id() == task_id) UserTaskFlag = true;
                }
            }
        }
        if (UserTaskFlag == false) throw new NotAuthorizedToTaskException(message);
    }

    public void isUnAuthorizedUserException(String message, Integer user_id, UserRepository userRepository) throws NotAuthorizedUserException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(principal.toString());
        if (user.getUser_id() != user_id) throw new NotAuthorizedUserException(message);
    }

    public void isUnAuthorizedUserToTeamException(String message, Team team, UserRepository userRepository) throws NotAuthorizedUserException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(principal.toString());

        boolean isTeamMemberFlag = false;
        for (User u : team.getUserSet()) {
            if (u.getUser_id() == user.getUser_id()) isTeamMemberFlag = true;
        }

        if (isTeamMemberFlag == false) throw new NotAuthorizedUserException(message);
    }

    public void isUnAuthorizedUserToWallException(String message, Integer user_id, Integer wall_id, UserRepository userRepository) throws NotAuthorizedUserException {
        boolean isWallFlag = false;
        for (Wall wall : userRepository.findById(user_id).get().getWallSet()) {
            if (wall.getWall_id() == wall_id) isWallFlag = true;
        }
        if (isWallFlag == false) throw new NotAuthorizedUserException(message);
    }

    public void isUnAuthorizedUserToBoardException(String message, Integer user_id, Integer board_id, UserRepository userRepository) throws NotAuthorizedUserToBoardException {

        boolean isBoardFlag = false;
        for (Wall wall : userRepository.findById(user_id).get().getWallSet()) {
            for (Board board : wall.getBoards()) {
                if (board.getBoard_id() == board_id) isBoardFlag = true;
            }
        }
        if (isBoardFlag == false) throw new NotAuthorizedUserToBoardException(message);
    }

    public void isMemberFormEmpty(MemberForm memberForm) throws EmptyInputException {
        if (memberForm.getMember_id() == null) throw new EmptyInputException("Empty member ID input");
        if (memberForm.getTeam_id() == null) throw new EmptyInputException("Empty team ID input");
    }

    public void isTaskFormEmpty(TaskForm taskForm) throws EmptyInputException {
        if (taskForm.getBoard_id() == null) throw new EmptyInputException("Empty board ID input");
        if (taskForm.getDescription() == null) throw new EmptyInputException("Empty description input");
        if (taskForm.getLabelName() == null || taskForm.getLabelName() == "")
            throw new EmptyInputException("Empty label name input");
    }

    public void isWallFormEmpty(WallForm wallForm) throws EmptyInputException {
        if (wallForm.getWall_id() == null) throw new EmptyInputException("Empty wall ID input");

    }
}
