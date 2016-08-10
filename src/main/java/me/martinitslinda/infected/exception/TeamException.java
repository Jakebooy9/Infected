package me.martinitslinda.infected.exception;

import me.martinitslinda.infected.team.Team;

public class TeamException extends Exception{

    private String message;
    private Team team;

    public TeamException(String message, Team team){
        this.message=message;
        this.team=team;
    }

    @Override
    public String getMessage(){
        return message;
    }

    public Team getTeam(){
        return team;
    }

}
