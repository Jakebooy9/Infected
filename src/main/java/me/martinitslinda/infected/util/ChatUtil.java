package me.martinitslinda.infected.util;

import net.md_5.bungee.api.ChatColor;

public class ChatUtil{

    public static String colorize(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
