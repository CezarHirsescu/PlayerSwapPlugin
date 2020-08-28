package me.cezar.Plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class Commands implements Listener, CommandExecutor {
    private final SwapPlugin plugin;

    private static final String errorGameStarted = ChatColor.RED + "The game has already started";
    private static final String errorGameNotStarted = ChatColor.RED + "The game has not started yet";
    private static final String errorNotEnoughPeople = ChatColor.RED + "There need to be at least two people in the server for the game to begin";

    public static final String cmdStart = "start_swap";
    public static final String cmdStop = "stop_swap";
    public static final String cmdManualSwap = "manual_swap";


    public Commands(SwapPlugin plugin) {
        this.plugin = plugin;
    }

    private String swapMessage(String seconds) {
        return ChatColor.GOLD + "Swapping in " + ChatColor.AQUA +  seconds + " seconds!";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // none of these commands should have arguments
        if (args.length > 0) {
            return false;
        }

        // going through the possible commands
        // cmd: start the game
        if (cmd.getName().equals(cmdStart)) {

            // check to see if game has already started or if there are enough people
            if (plugin.isGameStarted()) {
                sender.sendMessage(errorGameStarted);
                return false;
            } else if (plugin.getPlayers().size() < 2) {
                sender.sendMessage(errorNotEnoughPeople);
                return false;
            }

            // all tests passed, we can now start game
            plugin.setGameStarted(true);

            // these tasks print messages 10 seconds before the swap
            for (int i = 10; i > 0; i--) {
                final String finalI = ((Integer) i).toString();
                Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage(swapMessage(finalI));
                    }
                }, (20 * 60 * 5) - i, 20 * 60 * 5); // 5 minutes between repeats
                // messages will come every second starting from ten before the swap to alert the players
            }

            // start the swap task
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.doSwap();
                }
            }, 20 * 60 * 5, 20 * 60 * 5); // 5 minutes between repeats

            // if code here is reached, the command executed properly
            return true;

        // cmd: stop the game
        } else if (cmd.getName().equals(cmdStop)) {

            // check to see if game has started
            if (!plugin.isGameStarted()) {
                sender.sendMessage(errorGameNotStarted);
                return false;
            }

            // all tests passed
            // first get rid of all the tasks
            Bukkit.getScheduler().cancelTasks(plugin);

            // then stop the game
            plugin.setGameStarted(false);

            // if code reaches here the command worked properly
            return true;

        // cmd: do manual swap
        } else if (cmd.getName().equals(cmdManualSwap)) {

            // check to see if there are enough people to swap
            if (plugin.getPlayers().size() < 2) {
                sender.sendMessage(errorNotEnoughPeople);
                return false;
            }

            // the user can swap players even if the game has not started
            // all tests passed, preform swap
            plugin.doSwap();

            // if code is reached the command executed properly
            return true;

        }

        // no correct commands were entered
        return false;
    }
}
