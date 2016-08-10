package me.martinitslinda.infected.exception;

public class ArenaException extends Exception{

    private String message;

    public ArenaException(String message){
        this.message=message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
