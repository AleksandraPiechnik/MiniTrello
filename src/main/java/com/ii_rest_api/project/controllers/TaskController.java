package com.ii_rest_api.project.controllers;


import com.ii_rest_api.project.db.model.*;
import com.ii_rest_api.project.db.repositories.BoardRepository;
import com.ii_rest_api.project.db.repositories.TaskRepository;
import com.ii_rest_api.project.db.repositories.UserRepository;
import com.ii_rest_api.project.exceptions.EmptyInputException;
import com.ii_rest_api.project.exceptions.NotAuthorizedToTaskException;
import com.ii_rest_api.project.exceptions.TaskNotFoundException;
import com.ii_rest_api.project.utils.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    private ExceptionHandler exceptionHandler = new ExceptionHandler();

    @RequestMapping(value = "/{task_id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTask(@PathVariable Integer task_id) throws TaskNotFoundException, NotAuthorizedToTaskException, EmptyInputException {
        if (task_id == null) throw new EmptyInputException("Empty task ID input");
        if (!taskRepository.existsById(task_id)) throw new TaskNotFoundException("There is no " + task_id + " task ID");
        exceptionHandler.isUnAuthorizedToTaskException("You can delete only yours tasks", task_id, userRepository);
        taskRepository.deleteById(task_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @RequestMapping(value = "/{task_id}", method = RequestMethod.PUT)
    public Task moveTask(@PathVariable Integer task_id, @RequestBody TaskForm taskForm) throws TaskNotFoundException, NotAuthorizedToTaskException, EmptyInputException {
        exceptionHandler.isTaskFormEmpty(taskForm);
        if (!taskRepository.existsById(task_id)) throw new TaskNotFoundException("There is no " + task_id + " task ID");
        exceptionHandler.isUnAuthorizedToTaskException("You can move only yours tasks", task_id, userRepository);

        Task task = taskRepository.findById(task_id).get();
        if (taskForm.getLabelName() != task.getLabelName()) task.setLabelName(taskForm.getLabelName());
        if (taskForm.getDescription() != task.getDescription()) task.setDescription(taskForm.getDescription());
        if (taskForm.getBoard_id() != task.getBoard().getBoard_id()) {
            Board board = boardRepository.findById(taskForm.getBoard_id()).get();
            task.setBoard(board);
        }

        return taskRepository.save(task);
    }


}
