package me.martinitslinda.infected.event;

import me.martinitslinda.infected.player.InfectedPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKilledEvent extends Event{

    private static final HandlerList handlers=new HandlerList();

    private InfectedPlayer killed;
    private InfectedPlayer killer;

    public PlayerKilledEvent(InfectedPlayer killed, InfectedPlayer killer){
        this.killed=killed;
        this.killer=killer;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    public InfectedPlayer getKilled(){
        return killed;
    }

    public InfectedPlayer getKiller(){
        return killer;
    }

    @Override
    public HandlerList getHandlers(){
        return handlers;
    }

}
