package me.martinitslinda.infected.arena;

import com.flowpowered.nbt.*;
import com.flowpowered.nbt.stream.NBTInputStream;

import me.martinitslinda.infected.Infected;
import me.martinitslinda.infected.player.InfectedPlayer;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;

public class Arena{

    private int id;
    private String name;
    private String creator;
    private Location spawn1;
    private Location spawn2;

    private boolean loaded;
    private boolean disabled;

    private Infected plugin=Infected.getPlugin();

    private List<InfectedPlayer> voters=new ArrayList<>();

    public Arena(int id, String name, String creator, Location spawn1, Location spawn2, boolean disabled){
        this.id=id;
        this.name=name;
        this.creator=creator;
        this.spawn1=spawn1;
        this.spawn2=spawn2;
        this.disabled=disabled;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getCreator(){
        return creator;
    }

    public Location getSpawn1(){
        return spawn1;
    }

    public Location getSpawn2(){
        return spawn2;
    }

    public List<InfectedPlayer> getVoters(){
        return voters;
    }

    public boolean isLoaded(){
        return loaded;
    }

    public void setLoaded(boolean loaded){
        this.loaded=loaded;
    }

    public boolean isDisabled(){
        return disabled;
    }

    public void setDisabled(boolean disabled){
        this.disabled=disabled;
    }

}
