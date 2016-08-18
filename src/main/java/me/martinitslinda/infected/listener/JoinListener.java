package me.martinitslinda.infected.listener;

import me.martinitslinda.infected.Infected;
import me.martinitslinda.infected.arena.Arena;
import me.martinitslinda.infected.arena.ArenaManager;
import me.martinitslinda.infected.game.GameManager;
import me.martinitslinda.infected.game.GameState;
import me.martinitslinda.infected.player.InfectedPlayer;
import me.martinitslinda.infected.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener{

    private Infected plugin=Infected.getPlugin();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player pl=event.getPlayer();

        pl.setMaxHealth(20);
        pl.setHealth(20);
        pl.setFoodLevel(20);
        pl.getInventory().clear();
        pl.getInventory().setArmorContents(null);
        pl.setGameMode(GameMode.ADVENTURE);
        pl.setFlying(false);
        pl.setAllowFlight(false);
        pl.setFireTicks(0);

        if(GameManager.getState()==GameState.LOBBY){

            for(Arena arena : ArenaManager.getSelection()){

            }

        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, ()->{

            InfectedPlayer player=PlayerManager.getPlayer(pl.getUniqueId());

            if(GameManager.getState()==GameState.LOBBY){

                //Load stats scoreboard.

            }else if(GameManager.getState()==GameState.INGAME){

                //Load game stats scoreboard.

            }

        });

    }

}
