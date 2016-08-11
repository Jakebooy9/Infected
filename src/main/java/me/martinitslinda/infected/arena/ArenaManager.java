package me.martinitslinda.infected.arena;

import me.martinitslinda.infected.Infected;
import me.martinitslinda.infected.exception.ArenaException;
import me.martinitslinda.infected.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ArenaManager{

    private static List<Arena> arenas=new ArrayList<>();
    private static List<Arena> selection=new ArrayList<>();
    private static Arena arena=null;
    private static Infected plugin=Infected.getPlugin();

    public static void downloadArenas() throws SQLException, ArenaException{

        Infected.debug("Downloading Arenas...");

        List<Arena> arenas=new ArrayList<>();

        long startTime=System.currentTimeMillis();

        Connection connection=MySQL.getConnection();
        PreparedStatement statement=connection.prepareStatement("SELECT * FROM infected_arenas");
        ResultSet set=statement.executeQuery();

        while(set.next()){
            int id=set.getInt("id");

            String name=set.getString("name");
            String creator=set.getString("creator");

            boolean disabled=set.getBoolean("disabled");

            Location spawn1=new Location(Bukkit.getWorld(set.getString("world")),
                    set.getInt("spawn1x"), set.getInt("spawn1y"), set.getInt("spawn1"));
            Location spawn2=new Location(Bukkit.getWorld(set.getString("world")),
                    set.getInt("spawn2x"), set.getInt("spawn2y"), set.getInt("spawn2z"));

            Arena arena=new Arena(id, name, creator, spawn1, spawn2, disabled);

            arenas.add(arena);
        }

        set.close();
        statement.close();

        long endTime=System.currentTimeMillis();
        long totalTime=(endTime-startTime);

        setArenas(arenas);

        Infected.debug("Downloaded "+arenas.size()+" arena(s) in "+totalTime+"ms.");

        Infected.debug("Selecting arenas...");

        Set<Arena> selection=new HashSet<>();
        Random random=new Random();

        while(selection.size()<(arenas.size()>=5 ? 5 : arenas.size())){
            Arena arena=getArenas().get(random.nextInt(getArenas().size()));
            if(!(arena.isDisabled())){
                selection.add(arena);
            }
        }

        setSelection(new ArrayList<>(selection));

        endTime=System.currentTimeMillis();
        totalTime=(endTime-startTime);

        Infected.debug("Arena download and selection complete, Total time "+totalTime+"ms.");
    }

    public static List<Arena> getArenas(){
        return arenas;
    }

    public static void setArenas(List<Arena> arenas){
        ArenaManager.arenas=arenas;
    }

    public static List<Arena> getSelection(){
        return selection;
    }

    public static void setSelection(List<Arena> selection){
        ArenaManager.selection=selection;
    }

    public static Arena getArena(){
        return arena;
    }

    public static void setArena(Arena arena){
        ArenaManager.arena=arena;
    }

    public static Arena getById(int id){
        for(Arena arena : getArenas()){
            if(arena.getId()==id){
                return arena;
            }
        }
        return null;
    }

    public static Arena getByName(String name){
        for(Arena arena : getArenas()){
            if(arena.getName().equalsIgnoreCase(name)){
                return arena;
            }
        }
        return null;
    }

    public static List<Arena> getByCreator(String creator){
        return getArenas().stream().filter(arena->arena.getCreator()
                .equalsIgnoreCase(creator)).collect(Collectors.toList());
    }

    public static Arena create(String name, String creator) throws SQLException, ArenaException{

        Infected.debug("Creating a new arena...");

        if(getByName(name)!=null){
            Infected.debug("Duplicate arena name \""+name+"\".");
            throw new ArenaException("Duplicate arena name \""+name+"\".");
        }

        long startTime=System.currentTimeMillis();

        Connection connection=MySQL.getConnection();
        PreparedStatement statement=connection.prepareStatement("INSERT INTO infected_arenas (name, creator) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, name);
        statement.setString(2, creator);

        statement.executeUpdate();

        Infected.debug("Retrieving generated keys...");

        ResultSet set=statement.getGeneratedKeys();

        int id;

        if(set.next()){
            id=set.getInt(1);
        }else{
            Infected.debug("Unable to retrieve generated keys.");
            throw new SQLException("Unable to retrieve generated keys.");
        }

        set.close();
        statement.close();

        if(id<1){
            Infected.debug("Generated key must be greater than 0.");
            throw new IllegalStateException("Generated key must be greater than 0.");
        }

        if(getById(id)!=null){
            Infected.debug("Found duplicate Arena for id "+id);
            throw new ArenaException("Found duplicate Arena for id "+id);
        }

        Arena arena=new Arena(id, name, creator, null, null, false);

        getArenas().add(arena);

        long endTime=System.currentTimeMillis();

        long totalTime=(endTime-startTime);

        Infected.debug("Arena creation complete. Took "+totalTime+"ms.");

        return arena;
    }

    public void delete(int id) throws SQLException, ArenaException{

        Infected.debug("Arena deletion in progress");

        if(getById(id)!=null){
            Infected.debug("There's no Arena with id "+id+".");
            throw new ArenaException("There's no Arena with id "+id+".");
        }

        long startTime=System.currentTimeMillis();

        Connection connection=MySQL.getConnection();
        PreparedStatement statement=connection.prepareStatement("DELETE FROM infected_arenas WHERE id = ?");

        statement.setInt(1, id);
        statement.executeQuery();

        statement.close();

        long endTime=System.currentTimeMillis();

        //TODO: Reselect arena's so there's no future errors.

    }


}
