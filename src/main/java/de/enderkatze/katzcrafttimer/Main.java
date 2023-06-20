package de.enderkatze.katzcrafttimer;

import de.enderkatze.katzcrafttimer.commands.TimerCommand;
import de.enderkatze.katzcrafttimer.timer.Timer;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    private Timer timer;

    private NamespacedKey hologramKey;

    @Override
    public void onLoad() {
        instance = this;
        hologramKey = new NamespacedKey(Main.getInstance(), "timerHologram");
    }

    @Override
    public void onEnable() {

        // Plugin startup logic
        timer = new Timer(false, 0, false, true);

        saveDefaultConfig();
        reloadConfig();

        if(getConfig().getBoolean("saveTime")) {
            timer.setTime(getConfig().getInt("time"));
        }

        if(!getConfig().getBoolean("pauseOnRestart")) {
            timer.setRunning(true);
        }

        timer.setBackwards(getConfig().getBoolean("currentlyBackwards"));

        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("timer").setTabCompleter(new TimerCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if(getConfig().getBoolean("saveTime")) {
            getConfig().set("time", timer.getTime());
        }

        getConfig().set("currentlyBackwards", timer.getBackwards());

        saveConfig();

    }

    public static Main getInstance() {
        return instance;
    }

    public NamespacedKey getHologramKey() {

        return hologramKey;
    }

    public Timer getTimer() {
        return timer;
    }

    public String getPrefix() {
        String text = getConfig().getString("prefix");
        return "[" + text + ChatColor.RESET + "] ";
    }
}
