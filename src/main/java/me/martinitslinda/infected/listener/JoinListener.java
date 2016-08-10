package me.martinitslinda.infected.listener;

import me.martinitslinda.infected.Infected;
import me.martinitslinda.infected.arena.Arena;
import me.martinitslinda.infected.arena.ArenaManager;
import me.martinitslinda.infected.game.GameManager;
import me.martinitslinda.infected.game.GameState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener{

    private Infected plugin=Infected.getPlugin();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player=event.getPlayer();

        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.ADVENTURE);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setFireTicks(0);

        if(GameManager.getState()==GameState.LOBBY){

            for(Arena arena : ArenaManager.getSelection()){

            }

        }

    }

}
