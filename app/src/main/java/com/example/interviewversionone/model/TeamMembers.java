package com.example.interviewversionone.model;

public class TeamMembers {
    String Mobile,Name, TeamId, MembersId,Task;

    public TeamMembers() {
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }

    public String getMembersId() {
        return MembersId;
    }

    public void setMembersId(String membersId) {
        MembersId = membersId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTeamId() {
        return TeamId;
    }

    public void setTeamId(String teamId) {
        TeamId = teamId;
    }
}
