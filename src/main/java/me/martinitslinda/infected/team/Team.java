package me.martinitslinda.infected.team;

import me.martinitslinda.infected.player.InfectedPlayer;
import org.bukkit.ChatColor;

import java.util.Set;

public class Team{

    private String name;
    private ChatColor color;
    private Set<InfectedPlayer> members;

    public Team(String name, ChatColor color){
        this.name=name;
        this.color=color;
    }

    public String getName(){
        return name;
    }

    public ChatColor getColor(){
        return color;
    }

    public Set<InfectedPlayer> getMembers(){
        return members;
    }

}

