package me.martinitslinda.infected.listener;

import me.martinitslinda.infected.player.InfectedPlayer;
import me.martinitslinda.infected.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener{

    @EventHandler
    public void onEntityDeath(EntityDeathEvent evt){
        evt.setDroppedExp(0);
        if(evt instanceof PlayerDeathEvent){
            PlayerDeathEvent event=(PlayerDeathEvent) evt;

            event.getEntity().getInventory().clear();
            event.getEntity().getInventory().setArmorContents(null);
            event.setNewTotalExp(event.getEntity().getTotalExperience());
            event.setNewLevel(event.getEntity().getLevel());

            InfectedPlayer killed=PlayerManager.getPlayer(event.getEntity());
            InfectedPlayer killer=killed.getLastDamager();

            PlayerManager.handleDeath(killed, killer);
        }
    }

}
