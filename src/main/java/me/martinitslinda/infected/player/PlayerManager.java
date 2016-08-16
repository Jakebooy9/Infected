package me.martinitslinda.infected.player;

import me.martinitslinda.infected.Infected;
import me.martinitslinda.infected.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerManager{

    private static Map<UUID, InfectedPlayer> players=new HashMap<>();

    public static Map<UUID, InfectedPlayer> getPlayers(){
        return players;
    }

    public static InfectedPlayer getPlayer(String name){
        return null;
    }

    public static InfectedPlayer getPlayer(Player player){
        return getPlayer(player.getUniqueId());
    }

    public static InfectedPlayer getPlayer(UUID uuid){

        if(players.get(uuid)!=null){
            return players.get(uuid);
        }

        Infected.debug("Retrieving stats for '"+uuid+"'.");

        InfectedPlayer pl=new InfectedPlayer(uuid);

        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet set=null;

        try{
            connection=MySQL.getConnection();
            statement=connection.prepareStatement("SELECT * FROM infected_stats WHERE uuid = ?");
            statement.setString(1, uuid.toString());
            set=statement.executeQuery();

            boolean found=false;

            while(set.next()){

                if(found){
                    Infected.debug("Found multiple results for '"+uuid+"'.");
                    throw new SQLException("Found multiple results for '"+uuid+"'.");
                }

                found=true;

                pl.setWins(set.getInt("wins"));
                pl.setLosses(set.getInt("losses"));
                pl.setPlayersInfected(set.getInt("playersInfected"));
                pl.setTimesInfected(set.getInt("timesInfected"));
                pl.setLevel(set.getInt("level"));
                pl.setExperience(set.getLong("experience"));
                pl.setPrestigeLevel(set.getInt("prestigeLevel"));
                pl.setTotalPlayTime(set.getLong("totalPlayTime"));

            }

            if(!(found)){

                Infected.debug("'"+uuid+"' not found in database records, creating database entry...");

                statement=connection.prepareStatement("INSERT INTO infected_stats (uuid, username) VALUES (?, ?);");
                statement.setString(1, uuid.toString());
                statement.setString(2, pl.getPlayer().getName());
                statement.executeUpdate();

            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{

            Infected.debug("Closing resources...");

            if(set!=null){
                try{
                    set.close();
                }
                catch(SQLException ignored){

                }
            }

            if(statement!=null){
                try{
                    statement.close();
                }
                catch(SQLException ignored){

                }
            }

            if(connection!=null){
                try{
                    connection.close();
                }
                catch(SQLException ignored){

                }
            }
        }

        Infected.debug("Adding '"+uuid+"' to player cache...");

        players.put(uuid, pl);
        return pl;
    }

    public static Set<InfectedPlayer> getOnlinePlayers(){
        return Bukkit.getOnlinePlayers().stream()
                .map((Function<Player, InfectedPlayer>) PlayerManager::getPlayer).collect(Collectors.toSet());
    }
}
