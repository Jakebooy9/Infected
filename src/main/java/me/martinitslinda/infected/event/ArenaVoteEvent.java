package me.martinitslinda.infected.event;

import me.martinitslinda.infected.arena.Arena;
import me.martinitslinda.infected.player.InfectedPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaVoteEvent extends Event implements Cancellable{

    private static final HandlerList handlers=new HandlerList();
    private boolean isCancelled;
    private InfectedPlayer voter;
    private Arena arena;

    public ArenaVoteEvent(InfectedPlayer voter, Arena arena){
        this.voter=voter;
        this.arena=arena;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    public InfectedPlayer getVoter(){
        return voter;
    }

    public Arena getArena(){
        return arena;
    }

    @Override
    public HandlerList getHandlers(){
        return handlers;
    }

    @Override
    public boolean isCancelled(){
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled){
        this.isCancelled=isCancelled;
    }

}
