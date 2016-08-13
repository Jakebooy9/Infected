package me.martinitslinda.infected.command;

import me.martinitslinda.infected.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler{

    private static List<InfectedCommand> commands=new ArrayList<>();

    public static boolean handle(CommandSender sender, Command cmd, String[] args){

        InfectedCommand command=CommandHandler.getCommand(cmd.getName());

        if(command==null){
            return false;
        }else if((sender instanceof Player)&&!(command.isConsole())){
            return true;
        }else if(!(sender.hasPermission(command.getPermission()))){
            Message.get("no_permission")
                    .replace("%command%", cmd.getName())
                    .replace("%permission%", command.getPermission())
                    .sendTo(sender);
            return true;
        }else{
            String[] newArgs=new String[args.length-1];
            System.arraycopy(args, 1, newArgs, 0, args.length-1);
            command.execute(sender, newArgs);
        }
        return true;
    }

    public static List<InfectedCommand> getCommands(){
        return commands;
    }

    public static InfectedCommand getCommand(String name){
        for(InfectedCommand command : getCommands()){
            if(command.getName().equalsIgnoreCase(name)){
                return command;
            }
        }
        return null;
    }

    public static void register(InfectedCommand command){
        if(getCommand(command.getName())==null){
            getCommands().add(command);
        }
    }

    public static void unregister(InfectedCommand command){
        if(getCommand(command.getName())!=null){
            getCommands().remove(command);
        }
    }

}
