package me.martinitslinda.infected.listener;

import me.martinitslinda.infected.player.InfectedPlayer;
import me.martinitslinda.infected.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.lang.reflect.InvocationTargetException;

public class DeathListener implements Listener{

    @EventHandler
    public void onEntityDeath(EntityDeathEvent evt){
        evt.setDroppedExp(0);
        if(evt instanceof PlayerDeathEvent){
            PlayerDeathEvent event=(PlayerDeathEvent) evt;
            event.setDeathMessage(null);
            event.setNewLevel(0);
            event.setNewTotalExp(0);
            event.setNewExp(0);

            InfectedPlayer player=PlayerManager.getPlayer(event.getEntity());
            InfectedPlayer killer=player.getLastDamager();

            player.setDeaths(player.getDeaths()+1);

            if(!(player.isInfected())){
                player.setInfected(true);
            }
            if(killer!=null){
                player.setTimesInfected(player.getTimesInfected()+1);
                killer.setKillStreak(killer.getKillStreak()+1);
                killer.setKills(killer.getKills()+1);
            }

            try{
                Object t=player.getPlayer().getClass().getMethod("getHandle", new Class[0]).invoke(player.getPlayer());
                Object packet=Class.forName(t.getClass().getPackage().getName()+".PacketPlayInClientCommand").newInstance();
                Class enumClass=Class.forName(t.getClass().getPackage().getName()+".EnumClientCommand");
                Object[] consts;
                int len=(consts=enumClass.getEnumConstants()).length;

                Object respawn;
                for(int i=0; i<len; i++){
                    respawn=consts[i];
                    if(respawn.toString().equals("PERFORM_RESPAWN")){
                        packet=packet.getClass().getConstructor(new Class[]{enumClass}).newInstance(respawn);
                    }
                }

                respawn=t.getClass().getField("playerConnection").get(t);
                respawn.getClass().getMethod("a", new Class[]{packet.getClass()}).invoke(respawn, packet);
            }
            catch(InstantiationException|InvocationTargetException|IllegalAccessException|
                    NoSuchMethodException|ClassNotFoundException|NoSuchFieldException e){
                e.printStackTrace();
            }

        }
    }

}
