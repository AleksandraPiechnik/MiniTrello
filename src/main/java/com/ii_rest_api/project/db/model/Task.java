package com.ii_rest_api.project.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
@Entity(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer task_id;

    String labelName;
    String description;


    @ManyToOne
    @JoinColumn(name="board_id", nullable=false)
    private Board board;

    public Task() {
    }

    public Task(String labelName, String description, Board board) {
        this.labelName = labelName;
        this.description = description;
        this.board = board;
    }

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}

