package com.ii_rest_api.project.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "boards")
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer board_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "wall_id", nullable = false)
    private Wall wall;

    @JsonIgnore
    @OneToMany(mappedBy = "board")
    private Set<Task> taskSet;

    public Board() {
    }

    public Integer getBoard_id() {
        return board_id;
    }

    public void setBoard_id(Integer board_id) {
        this.board_id = board_id;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public Set<Task> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(Set<Task> taskSet) {
        this.taskSet = taskSet;
    }

    @Override
    public String toString() {
        return
                "board_id=" + board_id +
                        ", wall=" + wall +
                        ", taskSet=" + taskSet;
    }
}
