package me.martinitslinda.infected.command;

import me.martinitslinda.infected.Infected;
import org.bukkit.command.CommandSender;

public abstract class InfectedCommand{

    private String name;
    private String permission;
    private boolean console;

    protected Infected plugin=Infected.getPlugin();

    public InfectedCommand(String name, String permission, boolean console){
        this.name=name;
        this.permission=permission;
        this.console=console;
        register();
    }

    public abstract void execute(CommandSender sender, String[] args);

    public String getName(){
        return name;
    }

    public boolean isConsole(){
        return console;
    }

    public void setConsole(boolean console){
        this.console=console;
    }

    public String getPermission(){
        return permission;
    }

    public void register(){
        CommandHandler.register(this);
    }

    public void unregister(){
        CommandHandler.unregister(this);
    }

}
