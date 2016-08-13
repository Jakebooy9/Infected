package me.martinitslinda.infected.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener{

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){

        //event.setRespawnLocation(ArenaManager.getArena().getSpawn2());

        Player player=event.getPlayer();



    }

}
