package me.martinitslinda.infected.command;

import me.martinitslinda.infected.Infected;
import org.bukkit.command.CommandSender;

public abstract class InfectedCommand{

    public Infected plugin=Infected.getPlugin();

    private String name;
    private String permission;
    private String usage;
    private String description;
    private boolean console;

    public InfectedCommand(String name, String permission, String usage, String description, boolean console){
        this.name=name;
        this.permission=permission;
        this.usage=usage;
        this.description=description;
        this.console=console;
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

    public String getUsage(){
        return usage;
    }

    public String getDescription(){
        return description;
    }

    public void showHelp(CommandSender sender){

        StringBuilder builder=new StringBuilder();
        builder.append("/infected ").append(getName());
        if(getUsage()!=null){
            builder.append(" ").append(getUsage());
        }
        if(getDescription()!=null){
            builder.append(" - ").append(getDescription());
        }
        sender.sendMessage(builder.toString());

    }

    @Override
    public String toString(){
        return "InfectedCommand{"+
                "name='"+name+'\''+
                ", permission='"+permission+'\''+
                ", usage='"+usage+'\''+
                ", description='"+description+'\''+
                ", console="+console+
                '}';
    }

    public void register(){
        CommandHandler.register(this);
    }

    public void unregister(){
        CommandHandler.unregister(this);
    }

}
