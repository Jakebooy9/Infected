package me.martinitslinda.infected;

import me.martinitslinda.infected.arena.ArenaManager;
import me.martinitslinda.infected.exception.ArenaException;
import me.martinitslinda.infected.mysql.MySQL;

import me.martinitslinda.infected.util.Config;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.logging.Level;

public class Infected extends JavaPlugin{

    private static Infected plugin;

    private boolean errorOnStartup;

    @Override
    public void onLoad(){
        setPlugin(this);

        Config.load(this);

        try(Connection connection=MySQL.getConnection()){

            DatabaseMetaData metaData=connection.getMetaData();

            if(!(metaData.getTables(null, null, "infected_stats", null).next())){

                debug("Can't find required table \"infected_stats\", creating it...");
                getLogger().log(Level.INFO, "Can't find required table \"infected_stats\", creating it...");

                String values="id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, "+
                        "uuid VARCHAR (36) NOT NULL, "+
                        "username VARCHAR (16) NOT NULL, "+
                        "wins INT (11) NOT NULL DEFAULT '0', "+
                        "losses INT (11) NOT NULL DEFAULT '0', "+
                        "playersInfected INT (11) NOT NULL DEFAULT '0', "+
                        "timesInfected INT (11) NOT NULL DEFAULT '0', "+
                        "level INT (11) NOT NULL DEFAULT '0', "+
                        "prestigeLevel INT (11) NOT NULL DEFAULT '0',"+
                        "totalPlayTime BIGINT (64) NOT NULL DEFAULT '0'";
                String statement="CREATE TABLE infected_stats ("+values+");";

                connection.createStatement().executeUpdate(statement);

                if(metaData.getTables(null, null, "infected_stats", null).next()){
                    debug("Table \"infected_stats\" has been created!");
                    getLogger().log(Level.INFO, "Table \"infected_stats\" has been created!");
                }else{
                    debug("Something went wrong when creating table \"infected_stats\", please check the console log for more details");
                    getLogger().log(Level.INFO, "Something went wrong when creating table \"infected_stats\", "+
                            "please execute the statement below in your MySQL console.");
                    getLogger().log(Level.INFO, "\""+statement+"\"");
                    errorOnStartup=true;
                }
            }

            if(!(metaData.getTables(null, null, "infected_arenas", null).next())){

                getLogger().log(Level.INFO, "Can't find required table \"infected_arenas\", creating it...");

                String values="id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, "+
                        "name VARCHAR (50) NOT NULL, "+
                        "creator VARCHAR (50) NOT NULL, "+
                        "world VARCHAR (50) NOT NULL DEFAULT 'world', "+
                        "spawn1x INT (11) NOT NULL DEFAULT '0', "+
                        "spawn1y INT (11) NOT NULL DEFAULT '0', "+
                        "spawn1z INT (11) NOT NULL DEFAULT '0', "+
                        "spawn2x INT (11) NOT NULL DEFAULT '0', "+
                        "spawn2y INT (11) NOT NULL DEFAULT '0', "+
                        "spawn2z INT (11) NOT NULL DEFAULT '0'" +
                        "disabled TINYINT(1) NOT NULL DEFAULT '0'";
                String statement="CREATE TABLE infected_arenas ("+values+");";

                connection.createStatement().executeUpdate(statement);

                if(metaData.getTables(null, null, "infected_arenas", null).next()){
                    debug("Table \"infected_arenas\" has been created!");
                    getLogger().log(Level.INFO, "Table \"infected_arenas\" has been created!");
                }else{
                    debug("Something went wrong when creating table \"infected_arenas\", please check the console log for more details.");
                    getLogger().log(Level.INFO, "Something went wrong when creating table \"infected_arenas\", "+
                            "please execute the statement below in your MySQL console.");
                    getLogger().log(Level.INFO, "\""+statement+"\"");
                    errorOnStartup=true;
                }
            }

        }
        catch(SQLException e){
            e.printStackTrace();
            errorOnStartup=true;
        }

    }

    @Override
    public void onEnable(){
        if(errorOnStartup){
            debug("An error occurred on startup, please read the log for more details.");
            getLogger().log(Level.INFO, "An error occurred on startup, please read the log for more details.");
            getPluginLoader().disablePlugin(this);
            return;
        }

        try{
            ArenaManager.downloadArenas();
        }
        catch(SQLException | ArenaException e){
            e.printStackTrace();
        }

        final PluginManager pm=getServer().getPluginManager();

    }

    @Override
    public void onDisable(){

        MySQL.close();

    }

    public static Infected getPlugin(){
        return plugin;
    }

    public static void setPlugin(Infected plugin){
        Infected.plugin=plugin;
    }

    public static void debug(String message){

        Infected.getPlugin().getLogger().log(Level.INFO, message);

    }

}
