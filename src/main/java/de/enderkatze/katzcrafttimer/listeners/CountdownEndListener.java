package de.enderkatze.katzcrafttimer.listeners;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.events.CountdownEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CountdownEndListener implements Listener {

    @EventHandler
    public void onCountdownEnd(CountdownEndEvent event) {

        Main.getInstance().getTimer().setRunning(false);
        Main.getInstance().getTimer().setBackwards(false);
        Bukkit.broadcastMessage(Main.getInstance().getPrefix() + Main.getInstance().getLanguage().getString("actionbarTimeOverMessage"));

        if(Main.getInstance().getConfig().getString("countdownFinishedCommand") != null) {

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Main.getInstance().getConfig().getString("countdownFinishedCommand"));
        }
    }
}
