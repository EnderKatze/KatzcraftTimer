package de.enderkatze.katzcrafttimer.core.utitlity;

import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class TimerExpansion extends PlaceholderExpansion {

    private final KatzcraftTimer plugin;

    public TimerExpansion(KatzcraftTimer plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "timer";
    }

    @Override
    public @NotNull String getAuthor() {
        return "EnderKatze";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {

        return true;
    }

    @Override
    public boolean persist() {

        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {

        if(p == null) {
            return "";
        }


        switch (params) {

            case "current":
                return String.valueOf(plugin.getTimer().getTime());

            case "time":
                return plugin.getTimer().getTimeString();

            case "actionbar_visible":
                return String.valueOf(plugin.getToggledActionbarPlayers().contains(p));

            case "running":
                return String.valueOf(plugin.getTimer().isRunning());

            case "backwards":
                return String.valueOf(plugin.getTimer().isBackwards());
        }

        return null;
    }
}
