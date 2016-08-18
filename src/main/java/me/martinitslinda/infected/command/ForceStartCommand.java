package me.martinitslinda.infected.command;

import me.martinitslinda.infected.game.GameManager;
import me.martinitslinda.infected.game.GameState;
import org.bukkit.command.CommandSender;

public class ForceStartCommand extends InfectedCommand{

    public ForceStartCommand(){
        super("forcestart", "infected.command.forcestart", "[arena]", "Force the game to start on a specific Arena.", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args){

        if(GameManager.getState()!=GameState.LOBBY){
            return;
        }

    }
}
