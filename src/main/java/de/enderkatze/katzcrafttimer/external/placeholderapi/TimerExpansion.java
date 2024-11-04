package de.enderkatze.katzcrafttimer.external.placeholderapi;

import com.google.inject.Inject;
import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import de.enderkatze.katzcrafttimer.domain.contracts.timer.Timer;
import de.enderkatze.katzcrafttimer.domain.contracts.timer.TimerManager;
import de.enderkatze.katzcrafttimer.presenter.TimeFormatter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TimerExpansion extends PlaceholderExpansion {

    private final KatzcraftTimer plugin;
    private final TimerManager timerManager;

    @Inject
    public TimerExpansion(KatzcraftTimer plugin, TimerManager timerManager) {
        super();
        this.plugin = plugin;
        this.timerManager = timerManager;
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

        String id = params.split(" ")[0];


        Timer timer = id.equals("primary") ? timerManager.getPrimaryTimer() : timerManager.getTimer(id);

        if(timer == null) {
            return "Timer does not exist";
        }

        switch (params.split(" ")[1]) {

            case "time_value":
                return String.valueOf(timer.getTime());

            case "time_formatted":
                return TimeFormatter.Companion.getFormattedTime(timer.getTime());

            case "running":
                return String.valueOf(timer.getRunning());

            case "type":
                return timer.getType();
        }

        return null;
    }
}
