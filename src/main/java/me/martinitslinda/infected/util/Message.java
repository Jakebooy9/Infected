package me.martinitslinda.infected.util;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class Message{

    private static Map<String, String> messages=new HashMap<>();

    private String message;

    public Message(String key){
        this.message=messages.get(key);
    }

    public static Message get(String key){
        return new Message(key);
    }

    public static void load(FileConfiguration config){
        messages.clear();

        config.getConfigurationSection("messages").getKeys(false).stream()
                .forEach(message->messages.put(message, ChatUtil.colorize(config.getString("messages."+message))));
    }

    public Message replace(CharSequence replace, CharSequence replacement){
        this.message=message.replace(replace, replacement);
        return this;
    }

    public Message replace(CharSequence replace, Integer replacement){
        return replace(replace, Integer.toString(replacement));
    }

    public Message replace(CharSequence replace, Double replacement){
        return replace(replace, Double.toString(replacement));
    }

    public Message replace(CharSequence replace, Float replacement){
        return replace(replace, Float.toString(replacement));
    }

    public Message replace(CharSequence replace, Boolean replacement){
        return replace(replace, Boolean.toString(replacement));
    }

    public void sendTo(CommandSender sender){
        sender.sendMessage(message);
    }

    public void sendTo(Entity entity){
        if(entity!=null){
            entity.sendMessage(message);
        }
    }

    @Override
    public String toString(){
        return message;
    }

}
