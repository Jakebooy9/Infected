package me.martinitslinda.infected.player;

import me.martinitslinda.infected.Infected;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class InfectedPlayer{

    private int kills;
    private int deaths;
    private int killStreak;
    private int wins;
    private int losses;
    private int playersInfected;
    private int timesInfected;
    private int level;
    private int prestigeLevel;

    private boolean isSpectating;
    private boolean isDebugging;

    private float experience;

    private long totalPlayTime;
    private long joinTime;

    private InfectedPlayer lastDamager;

    private UUID uuid;

    public InfectedPlayer(UUID uuid){
        this.uuid=uuid;
    }

    public int getKills(){
        return kills;
    }

    public void setKills(int kills){
        this.kills=kills;
    }

    public int getDeaths(){
        return deaths;
    }

    public void setDeaths(int deaths){
        this.deaths=deaths;
    }

    public int getKillStreak(){
        return killStreak;
    }

    public void setKillStreak(int killStreak){
        this.killStreak=killStreak;
    }

    public int getWins(){
        return wins;
    }

    public void setWins(int wins){
        this.wins=wins;
    }

    public int getLosses(){
        return losses;
    }

    public void setLosses(int losses){
        this.losses=losses;
    }

    public int getPlayersInfected(){
        return playersInfected;
    }

    public void setPlayersInfected(int playersInfected){
        this.playersInfected=playersInfected;
    }

    public int getTimesInfected(){
        return timesInfected;
    }

    public void setTimesInfected(int timesInfected){
        this.timesInfected=timesInfected;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level=level;
    }

    public int getPrestigeLevel(){
        return prestigeLevel;
    }

    public void setPrestigeLevel(int prestigeLevel){
        this.prestigeLevel=prestigeLevel;
    }

    public boolean isSpectating(){
        return isSpectating;
    }

    public void setSpectating(boolean spectating){
        isSpectating=spectating;
    }

    public boolean isDebugging(){
        return isDebugging;
    }

    public void setDebugging(boolean debugging){
        isDebugging=debugging;
    }

    public long getTotalPlayTime(){
        return totalPlayTime;
    }

    public void setTotalPlayTime(long totalPlayTime){
        this.totalPlayTime=totalPlayTime;
    }

    public long getJoinTime(){
        return joinTime;
    }

    public void setJoinTime(long joinTime){
        this.joinTime=joinTime;
    }

    public float getExperience(){
        return experience;
    }

    public void setExperience(float experience){
        this.experience=experience;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public UUID getUuid(){
        return uuid;
    }

    public InfectedPlayer getLastDamager(){
        return lastDamager;
    }

    public void setLastDamager(InfectedPlayer lastDamager){
        this.lastDamager=lastDamager;
    }

    public void resetState(){
        setSpectating(false);

        Player player=getPlayer();

        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.ADVENTURE);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setFireTicks(0);
        player.eject();

        Entity entity=player.getPassenger();
        if(entity!=null){
            entity.eject();
        }

        if(Infected.getPlugin().getDisguiseAPI()!=null){
            Infected.getPlugin().getDisguiseAPI().undisguiseAll();
        }

    }

    public void addKill(){
        setKills(getKills()+1);
    }

    public void addDeath(){
        setDeaths(getDeaths()+1);
    }

    public void addWin(){
        setWins(getWins()+1);
    }

    public void addLoss(){
        setLosses(getLosses()+1);
    }

    public void addInfection(){
        setPlayersInfected(getPlayersInfected()+1);
    }

    public void addLevel(){
        setLevel(getLevel()+1);
    }

    public void addExperience(float experience){
        getPlayer().setExp(getPlayer().getExp()+experience);
        if(getPlayer().getLevel()>getLevel()){
            setLevel(getPlayer().getLevel());
            setExperience(getPlayer().getExp());
        }
    }

    public void updateStats(){

    }
}
