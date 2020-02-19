package com.ii_rest_api.project.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer user_id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int active;

    private String roles = "";

    private String permissions = "";

    // prawdopodobnie usunac
    @JsonIgnore
    @ManyToMany(mappedBy = "userSet")
    private Set<Team> teamSet = new HashSet<>();


    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    Set<Wall> wallSet = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Set<Team> getTeamSet() {
        return teamSet;
    }

    public void setTeamSet(Set<Team> teamSet) {
        this.teamSet = teamSet;
    }

    public Set<Wall> getWallSet() {
        return wallSet;
    }

    public void setWallSet(Set<Wall> wallSet) {
        this.wallSet = wallSet;
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return
                "user_id=" + user_id  ;
    }
}
