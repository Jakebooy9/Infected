package me.martinitslinda.infected.team;

import me.martinitslinda.infected.exception.TeamException;
import me.martinitslinda.infected.player.InfectedPlayer;

import java.util.ArrayList;
import java.util.List;

public class TeamManager{

    private static List<Team> teams=new ArrayList<>();

    public static void joinTeam(InfectedPlayer player, Team team) throws TeamException{

        if(player==null||team==null){
            throw new IllegalArgumentException((player==null ? "Player" : "Team")+" cannot be null.");
        }
        if(getTeam(team.getName())==null){
            throw new TeamException("Unregistered team: "+team.getName(), team);
        }

        for(Team t : getTeams()){
            if(t.getMembers().contains(player)){
                t.getMembers().remove(player);
            }
        }

        team.getMembers().add(player);

    }

    public static List<Team> getTeams(){
        return teams;
    }

    public static void setTeams(List<Team> teams){
        TeamManager.teams=teams;
    }

    public static Team getTeam(String name){
        for(Team team : getTeams()){
            if(team.getName().equalsIgnoreCase(name)){
                return team;
            }
        }
        return null;
    }

    public static Team getTeamOf(InfectedPlayer player){
        for(Team team : getTeams()){
            if(team.getMembers().contains(player)){
                return team;
            }
        }
        return null;
    }

}
