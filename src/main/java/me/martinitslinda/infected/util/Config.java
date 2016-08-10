package me.martinitslinda.infected.util;

import me.martinitslinda.infected.Infected;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Config{

    public static void load(Infected plugin){

        FileConfiguration config=plugin.getConfig();
        Map<String, Object> cfg=new HashMap<>();

        cfg.put("mysql.user", "root");
        cfg.put("mysql.password", "AVbHks21");
        cfg.put("mysql.hostname", "localhost");
        cfg.put("mysql.database", "testing_server");
        cfg.put("mysql.port", 3306);

        cfg.put("messages.no_console_access", "&cThe console cannot perform this command.");
        cfg.put("messages.no_permission", "&cYou don't have permission to do that.");

        cfg.put("version", plugin.getDescription().getVersion());

        cfg.entrySet().stream()
                .filter(e->!(config.contains(e.getKey())))
                .forEach(e->config.set(e.getKey(), e.getValue()));

        plugin.saveConfig();
        plugin.reloadConfig();

    }

}
