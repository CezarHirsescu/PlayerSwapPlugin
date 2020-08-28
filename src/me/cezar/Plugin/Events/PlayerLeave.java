package me.cezar.Plugin.Events;

import me.cezar.Plugin.SwapPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {
    private SwapPlugin plugin;

    public PlayerLeave(SwapPlugin plugin) {
        this.plugin = plugin;
    }

    public void onPlayerLeaveEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        plugin.getPlayers().remove(player);
        Bukkit.getServer().broadcastMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + " has left the game. Taking them off the switch roster");

        if (plugin.getPlayers().size() < 2) {
            plugin.setGameStarted(false);
            Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "There is only one person online. Stopping game");
        }
    }
}
