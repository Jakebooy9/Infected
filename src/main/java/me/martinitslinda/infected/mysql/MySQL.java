package me.martinitslinda.infected.mysql;

import com.zaxxer.hikari.HikariDataSource;
import me.martinitslinda.infected.Infected;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL{

    private static HikariDataSource source;
    private static Infected plugin=Infected.getPlugin();

    public static Connection getConnection() throws SQLException{
        try{
            if(source!=null&&!source.isClosed()){
                return source.getConnection();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        source=new HikariDataSource();

        source.setJdbcUrl(plugin.getSettings().getJdbcUrl());
        source.setUsername(plugin.getSettings().getUser());
        source.setPassword(plugin.getSettings().getPassword());
        source.setMaximumPoolSize(10);

        return source.getConnection();
    }

    public static HikariDataSource getSource(){
        return source;
    }

    public static void setSource(HikariDataSource source){
        MySQL.source=source;
    }

    public static void close(){
        if(getSource()!=null&&!getSource().isClosed()){
            getSource().close();
        }
        setSource(null);
    }

}
