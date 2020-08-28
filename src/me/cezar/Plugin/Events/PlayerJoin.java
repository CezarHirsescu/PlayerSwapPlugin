package me.cezar.Plugin.Events;

import me.cezar.Plugin.SwapPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private SwapPlugin plugin;

    public PlayerJoin(SwapPlugin plugin) {
        this.plugin = plugin;
    }

    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        plugin.getPlayers().add(player);
        Bukkit.getServer().broadcastMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + " has joined the game and will be added to the swap roster");
    }

}
