package com.ii_rest_api.project.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "walls")
public class Wall implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer wall_id;
    @JsonIgnore
    @ManyToMany(mappedBy = "wallSet")
    private Set<User> userSet = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "wall")
    private Set<Board> boards;

    public Wall() {
    }
    public Wall(User user) {
        userSet.add(user);
    }

    public Set<Board> getBoards() {
        return boards;
    }

    public void setBoards(Set<Board> boards) {
        this.boards = boards;
    }

    public Integer getWall_id() {
        return wall_id;
    }

    public void setWall_id(Integer wall_id) {
        this.wall_id = wall_id;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    @Override
    public String toString() {
        return
                "wall_id=" + wall_id +
                ", userSet=" + userSet +
                ", boards=" + boards ;
    }
}
