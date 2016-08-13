package me.martinitslinda.infected;

import de.robingrether.idisguise.api.DisguiseAPI;
import me.martinitslinda.infected.arena.ArenaManager;
import me.martinitslinda.infected.command.CommandHandler;
import me.martinitslinda.infected.exception.ArenaException;
import me.martinitslinda.infected.listener.DeathListener;
import me.martinitslinda.infected.listener.JoinListener;
import me.martinitslinda.infected.listener.RespawnListener;
import me.martinitslinda.infected.mysql.MySQL;
import me.martinitslinda.infected.util.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.logging.Level;

public class Infected extends JavaPlugin{

    private static Infected plugin;
    private boolean errorOnStartup;
    private Settings settings;
    private DisguiseAPI disguiseAPI;

    public static Infected getPlugin(){
        return plugin;
    }

    public static void setPlugin(Infected plugin){
        Infected.plugin=plugin;
    }

    public static void debug(String message){
        Infected.getPlugin().getLogger().log(Level.INFO, message);
    }

    @Override
    public void onLoad(){
        setPlugin(this);

        settings=new Settings();

        getSettings().load();

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
                        "spawn2z INT (11) NOT NULL DEFAULT '0'"+
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

        setDisguiseAPI(getServer().getServicesManager().getRegistration(DisguiseAPI.class).getProvider());

        try{
            ArenaManager.downloadArenas();
        }
        catch(SQLException|ArenaException e){
            e.printStackTrace();
        }

        debug("Registering commands...");


        debug("Registering events...");

        PluginManager pm=getServer().getPluginManager();
        pm.registerEvents(new DeathListener(), this);
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new RespawnListener(), this);

        debug("Registered "+HandlerList.getRegisteredListeners(this).size()+" listeners.");
    }

    @Override
    public void onDisable(){

        if(getDisguiseAPI()!=null){
            getDisguiseAPI().undisguiseAll();
        }

        MySQL.close();

    }

    public DisguiseAPI getDisguiseAPI(){
        return disguiseAPI;
    }

    public void setDisguiseAPI(DisguiseAPI disguiseAPI){
        this.disguiseAPI=disguiseAPI;
    }

    public Settings getSettings(){
        return settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        return command.getName().equalsIgnoreCase("infected")&&CommandHandler.handle(sender, command, args);
    }
}
