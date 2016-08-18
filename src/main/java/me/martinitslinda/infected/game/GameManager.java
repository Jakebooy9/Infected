package me.martinitslinda.infected.game;

import me.martinitslinda.infected.Infected;
import me.martinitslinda.infected.arena.Arena;
import me.martinitslinda.infected.arena.ArenaManager;
import me.martinitslinda.infected.team.Team;
import me.martinitslinda.infected.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameManager{

    private static GameState state;
    private static int ticks;

    public static void startGame(Arena arena){

        if(getState()!=GameState.LOBBY){
            throw new IllegalStateException("The game is already in progress.");
        }
        if(Bukkit.getOnlinePlayers().size()<5){
            throw new IllegalStateException("Insufficient players online to start.");
        }
        if(arena==null){
            throw new NullPointerException("Cannot start the game on a null Arena.");
        }
        if(arena.getSpawn1()==null||arena.getSpawn2()==null){
            throw new NullPointerException("Spawn location(s) cannot be null.");
        }
        if(arena.getSpawn1().getWorld()!=arena.getSpawn2().getWorld()){
            throw new IllegalStateException("Spawn locations cannot be of differing worlds.");
        }
        if(arena.getSpawn1().getX()==0||arena.getSpawn1().getY()==0||arena.getSpawn1().getZ()==0){
            throw new IllegalStateException("Arenas x, y, z co-ordinates must be greater than 0.");
        }
        if(arena.getSpawn2().getX()==0||arena.getSpawn2().getY()==0||arena.getSpawn2().getZ()==0){
            throw new IllegalStateException("The arenas x, y, z co-ordinates must be greater than 0.");
        }

    }

    public static void endGame(Team winner){

    }

    public static void init(){
        new BukkitRunnable(){
            @Override
            public void run(){
                doTick();
            }
        }.runTaskTimer(Infected.getPlugin(), 0L, 20L);
    }

    private static void doTick(){
        if(getTicks()>0){
            setTicks(getTicks()-1);
        }
        if(getState()==GameState.LOBBY){

            if(getTicks()%15==0){
                for(Player player : Bukkit.getOnlinePlayers()){
                    Message.get("start_game_countdown")
                            .replace("%remaining%", getTicks())
                            .sendTo(player);

                    for(Arena arena : ArenaManager.getSelection()){
                        Message.get("arena_list_format")
                                .replace("%arena%", arena.getName())
                                .replace("%creator%", arena.getCreator())
                                .replace("%votes%", arena.getVoters().size())
                                .sendTo(player);
                    }

                }
            }else if(getTicks()==0){
                //TODO: Start game with winning Arena.

                startGame(null);
            }

        }else if(getState()==GameState.INFECTION){

            if(getTicks()<=5&&getTicks()>0){

            }else if(getTicks()==0){

            }

        }else if(getState()==GameState.INGAME){

        }else{
            throw new IllegalStateException("Invalid GameState \""+getState()+"\".");
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
