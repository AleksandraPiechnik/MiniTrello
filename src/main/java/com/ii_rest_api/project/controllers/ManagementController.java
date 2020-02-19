package com.ii_rest_api.project.controllers;

import com.ii_rest_api.project.db.model.Team;
import com.ii_rest_api.project.db.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    private TeamRepository teamRepository;

    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public List<Team> getTeams() {
        List<Team> teamList= teamRepository.findAll();
        return teamList;
    }
}