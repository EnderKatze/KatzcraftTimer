package de.enderkatze.katzcrafttimer.core.listeners;

import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


// TODO Introduce dependency injection to fix
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {

        if(KatzcraftTimer.getInstance().updateAvailable && event.getPlayer().isOp()) {

            event.getPlayer().sendMessage(KatzcraftTimer.getInstance().getPrefix() + KatzcraftTimer.getInstance().getLanguage().getString("updateChecker.update").replace("{newVer}", KatzcraftTimer.getInstance().getNewestVersion()));
        }
    }
}
