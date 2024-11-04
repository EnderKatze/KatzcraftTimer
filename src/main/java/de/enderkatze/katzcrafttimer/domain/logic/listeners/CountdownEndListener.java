package de.enderkatze.katzcrafttimer.domain.logic.listeners;

import com.google.inject.Inject;
import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import de.enderkatze.katzcrafttimer.api.events.CountdownEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.logging.Level;

public class CountdownEndListener implements Listener {

    private final KatzcraftTimer instance;

    @Inject
    public CountdownEndListener(KatzcraftTimer instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onCountdownEnd(CountdownEndEvent event) {

        instance.getTimer().setRunning(false);
        instance.getTimer().setBackwards(false);
        Bukkit.broadcastMessage(instance.getPrefix() + instance.getLanguage().getString("actionbarTimeOverMessage"));

        List<String> commands = instance.getConfig().getStringList("countdownFinishedCommands");

        for (String command : commands) {
            if (command.startsWith("/")) {
                command = command.replace("/", "");
            }
            Bukkit.getLogger().log(Level.INFO, command);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }
}
