package me.martinitslinda.infected.game;

import me.martinitslinda.infected.arena.Arena;
import me.martinitslinda.infected.arena.ArenaManager;
import me.martinitslinda.infected.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameManager{

    private static GameState state;
    private static int ticks;

    public static void doTick(){

        if(getState()==GameState.LOBBY){

            if(getTicks()%15==0){
                for(Player player : Bukkit.getOnlinePlayers()){
                    Message.get("start_game_countdown")
                            .replace("%remaining%", getTicks())
                            .sendTo(player);
                    player.playSound(player.getLocation(), Sound.CLICK, 1, 1);

                    for(Arena arena : ArenaManager.getSelection()){
                        Message.get("arena_list_format")
                                .replace("%arena%", arena.getName())
                                .replace("%creator%", arena.getCreator())
                                .replace("%votes%", arena.getVoters().size())
                                .sendTo(player);
                    }

                }
            }

        }

    }

    public static GameState getState(){
        return state;
    }

    public static void setState(GameState state){
        GameManager.state=state;
    }

    public static int getTicks(){
        return ticks;
    }

    public static void setTicks(int ticks){
        GameManager.ticks=ticks;
    }

}
