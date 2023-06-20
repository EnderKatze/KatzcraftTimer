package de.enderkatze.katzcrafttimer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TimerExpansion extends PlaceholderExpansion {
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
                return String.valueOf(Main.getInstance().getTimer().getTime());

            case "actionbar_visible":
                return "";
        }

        return "";
    }
}
