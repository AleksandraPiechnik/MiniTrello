package com.ii_rest_api.project.db.model;

public class MemberForm {

    private Integer member_id;
    private Integer team_id;

    public MemberForm() {
    }

    public MemberForm(Integer member_id, Integer team_id) {
        this.member_id = member_id;
        this.team_id = team_id;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }
}
