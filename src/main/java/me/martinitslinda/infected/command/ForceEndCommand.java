package me.martinitslinda.infected.command;

import me.martinitslinda.infected.game.GameManager;
import me.martinitslinda.infected.game.GameState;
import org.bukkit.command.CommandSender;

public class ForceEndCommand extends InfectedCommand{

    public ForceEndCommand(){
        super("forceend", "infected.command.forceend", null, "Force the game to end in a draw.", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args){

        if(GameManager.getState()!=GameState.INGAME||GameManager.getState()!=GameState.INFECTION){
            return;
        }

        //Set the remaining game ticks to 0
        GameManager.setTicks(0);

    }
}
