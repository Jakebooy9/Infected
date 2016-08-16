package me.martinitslinda.infected.listener;

import de.robingrether.idisguise.api.DisguiseAPI;
import de.robingrether.idisguise.disguise.ZombieDisguise;
import me.martinitslinda.infected.Infected;
import me.martinitslinda.infected.event.PlayerKilledEvent;
import me.martinitslinda.infected.player.InfectedPlayer;
import me.martinitslinda.infected.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DeathListener implements Listener{

    @EventHandler
    public void onEntityDeath(EntityDeathEvent evt){
        evt.setDroppedExp(0);
        if(evt instanceof PlayerDeathEvent){
            PlayerDeathEvent event=(PlayerDeathEvent) evt;

            Infected.debug("Clearing player inventory and setting total exp and level to current exp level...");
            event.getEntity().getInventory().clear();
            event.getEntity().getInventory().setArmorContents(null);
            event.setKeepLevel(true);

            InfectedPlayer player=PlayerManager.getPlayer(event.getEntity());

            InfectedPlayer killer=player.getLastDamager();
            if(killer!=null){
                killer.addKill();
                killer.addInfection();
                killer.addExperience(250L);
            }

            Infected.debug("Adding death, resetting kill streak and resetting last damager...");

            player.addDeath();
            player.setLastDamager(null);
            player.setKillStreak(0);

            try{
                Infected.debug("Retrieving current Bukkit version...");

                String bukkitVersion=Bukkit.getServer().getClass()
                        .getPackage().getName().substring(23);

                Infected.debug("Server is currently running CraftBukkit version "+bukkitVersion+".");
                Infected.debug("Retrieving required classes...");

                Class<?> cp=Class.forName("org.bukkit.craftbukkit."
                        +bukkitVersion+".entity.CraftPlayer");
                Class<?> clientCmd=Class.forName("net.minecraft.server."
                        +bukkitVersion+".PacketPlayInClientCommand");
                Class enumClientCMD=Class.forName("net.minecraft.server."
                        +bukkitVersion+".PacketPlayInClientCommand$EnumClientCommand");

                Infected.debug("Retrieving player handle...");

                Method handle=cp.getDeclaredMethod("getHandle");

                Infected.debug("Invoking handle...");

                Object entityPlayer=handle.invoke(player.getPlayer());

                Infected.debug("Getting declared constructor for "+clientCmd.getClass().getName()+"...");

                Constructor<?> packetConstr=clientCmd
                        .getDeclaredConstructor(enumClientCMD);
                Enum<?> num=Enum.valueOf(enumClientCMD, "PERFORM_RESPAWN");

                Infected.debug("Creating a new instance of "+packetConstr.getName()+"...");

                Object packet=packetConstr.newInstance(num);

                Infected.debug("Retrieving player connection...");

                Object playerConnection=entityPlayer.getClass()
                        .getDeclaredField("playerConnection").get(entityPlayer);
                Method send=playerConnection.getClass().getDeclaredMethod("a",
                        clientCmd);

                Infected.debug("Forcing player respawn...");

                send.invoke(playerConnection, packet);
            }
            catch(InstantiationException|InvocationTargetException|IllegalAccessException|
                    NoSuchMethodException|ClassNotFoundException|NoSuchFieldException e){
                Infected.debug("An error occurred whilst attempting to force respawn "+player.getPlayer().getName()+", check the console for more details.");
                e.printStackTrace();
            }

            if(Infected.getPlugin().getDisguiseAPI()!=null){
                DisguiseAPI disguiseAPI=Infected.getPlugin().getDisguiseAPI();
                Infected.debug("DisguiseAPI found, Checking if "+player.getPlayer().getName()+" is already disguised...");
                if(!(disguiseAPI.isDisguised(player.getPlayer()))){
                    Infected.debug(player.getPlayer().getName()+" isn't currently disguised, Disguising...");
                    disguiseAPI.disguiseToAll(player.getPlayer(), new ZombieDisguise());
                }
            }

            Infected.debug("Calling PlayerKilledEvent...");
            Bukkit.getPluginManager().callEvent(new PlayerKilledEvent(player, player.getLastDamager()));

        }
    }

}
