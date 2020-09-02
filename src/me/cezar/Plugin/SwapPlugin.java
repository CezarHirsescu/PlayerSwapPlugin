package me.cezar.Plugin;

import me.cezar.Plugin.Events.PlayerJoin;
import me.cezar.Plugin.Events.PlayerLeave;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SwapPlugin extends JavaPlugin {
    private final Commands commands = new Commands(this);
    private List<Player> players = new ArrayList<>();
    private boolean isGameStarted = false;


    public List<Player> getPlayers() {
        return players;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    @Override
    public void onEnable() {

        getCommand(Commands.cmdStart).setExecutor(commands);
        getCommand(Commands.cmdStop).setExecutor(commands);
        getCommand(Commands.cmdManualSwap).setExecutor(commands);

        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(this), this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "(!) PlayerSwap plugin has been enabled");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "(!) PlayerSwap plugin has been disabled");
    }

    public void doSwap() {

        // the plugin needs at least 2 people to work
        if (players.size() < 2) {
            return;
        }

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            Location newLoc;
            try {
                newLoc = players.get(i + 1).getLocation();
            } catch (IndexOutOfBoundsException e) {
                newLoc = players.get(0).getLocation();
            }

            player.teleport(newLoc);
        }
    }

}
