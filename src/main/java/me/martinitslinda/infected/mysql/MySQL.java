package me.martinitslinda.infected.mysql;

import com.zaxxer.hikari.HikariDataSource;
import me.martinitslinda.infected.Infected;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL{

    private static HikariDataSource source;
    private static Infected plugin=Infected.getPlugin();

    public static String getUrl(){
        return "jdbc:mysql://"+getHost()+":"+getPort()+"/"+getDatabase();
    }

    public static String getHost(){
        return plugin.getConfig().getString("mysql.hostname");
    }

    public static int getPort(){
        return plugin.getConfig().getInt("mysql.port");
    }

    public static String getDatabase(){
        return plugin.getConfig().getString("mysql.database");
    }

    private static String getUsername(){
        return plugin.getConfig().getString("mysql.user");
    }

    public static String getPassword(){
        return plugin.getConfig().getString("mysql.password");
    }

    public static Connection getConnection() throws SQLException{
        try{
            if(source!=null&&!source.isClosed()) return source.getConnection();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        source=new HikariDataSource();

        source.setJdbcUrl(getUrl());
        source.setUsername(getUsername());
        source.setPassword(getPassword());
        source.setMaximumPoolSize(10);
        source.setLeakDetectionThreshold(5000L);

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
