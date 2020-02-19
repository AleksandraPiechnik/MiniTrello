package com.ii_rest_api.project.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name="teams")
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer team_id;


    @ManyToMany
    private Set<User> userSet = new HashSet<>();


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wall_id")
    private Wall wall;

    public Team() {
    }

    public Team(User user, Wall wall) {
        userSet.add(user);
        this.wall=wall;
    }

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    @Override
    public String toString() {
        return "Team{" +
                "team_id=" + team_id +
//                ", userSet=" + userSet +
//                ", wall=" + wall +
                '}';
    }
}
