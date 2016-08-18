package me.martinitslinda.infected.util;

import me.martinitslinda.infected.Infected;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Settings{

    private Infected plugin;
    private FileConfiguration config;

    public Settings(){
        plugin=Infected.getPlugin();
        config=plugin.getConfig();
    }

    public void load(){

        Infected.debug("Loading Configuration...");

        Map<String, Object> cfg=new HashMap<>();

        cfg.put("mysql.user", "root");
        cfg.put("mysql.password", "AVbHks21");
        cfg.put("mysql.hostname", "localhost");
        cfg.put("mysql.database", "testing_server");
        cfg.put("mysql.port", 3306);

        cfg.put("messages.no_console_access", "&cThe console cannot perform this command.");
        cfg.put("messages.no_permission", "&cYou don't have permission to do that.");

        cfg.entrySet().stream()
                .filter(e->!(config.contains(e.getKey())))
                .forEach(e->config.set(e.getKey(), e.getValue()));

        config.set("version", plugin.getDescription().getVersion());

        Infected.debug("Saving and reloading server config...");

        plugin.saveConfig();
        plugin.reloadConfig();

        Infected.debug("Configuration loaded!");

    }

    public String getJdbcUrl(){
        return "jdbc:mysql://"+getHost()+":"+getPort()+"/"+getDatabase();
    }

    public String getHost(){
        return config.getString("mysql.hostname");
    }

    public int getPort(){
        return config.getInt("mysql.port");
    }

    public String getDatabase(){
        return config.getString("mysql.database");
    }

    public String getUser(){
        return config.getString("mysql.user");
    }

    public String getPassword(){
        return config.getString("mysql.password");
    }

}
