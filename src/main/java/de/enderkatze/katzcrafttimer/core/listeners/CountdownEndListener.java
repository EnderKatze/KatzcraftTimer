package de.enderkatze.katzcrafttimer.core.listeners;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.api.events.CountdownEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.logging.Level;

public class CountdownEndListener implements Listener {

    @EventHandler
    public void onCountdownEnd(CountdownEndEvent event) {

        Main.getInstance().getTimer().setRunning(false);
        Main.getInstance().getTimer().setBackwards(false);
        Bukkit.broadcastMessage(Main.getInstance().getPrefix() + Main.getInstance().getLanguage().getString("actionbarTimeOverMessage"));

        List<String> commands = Main.getInstance().getConfig().getStringList("countdownFinishedCommands");

        for (String command : commands) {
            if (command.startsWith("/")) {
                command = command.replace("/", "");
            }
            Bukkit.getLogger().log(Level.INFO, command);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }
}
