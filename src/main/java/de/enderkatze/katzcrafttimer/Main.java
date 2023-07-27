package de.enderkatze.katzcrafttimer;

import de.enderkatze.easylanguages.EasyLanguages;

import de.enderkatze.katzcrafttimer.Utility.TimerExpansion;
import de.enderkatze.katzcrafttimer.Utility.UpdateChecker;
import de.enderkatze.katzcrafttimer.commands.TimerCommand;
import de.enderkatze.katzcrafttimer.listeners.PlayerJoinListener;
import de.enderkatze.katzcrafttimer.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    public boolean updateAvailable = false;

    private static Main instance;
    private Timer timer;

    private List<Player> toggledActionbarPlayers = new ArrayList<>();

    private NamespacedKey hologramKey;

    @Override
    public void onLoad() {
        instance = this;
        hologramKey = new NamespacedKey(Main.getInstance(), "timerHologram");
    }

    @Override
    public void onEnable() {

        new UpdateChecker(this, 104380).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info(EasyLanguages.GetServerLanguage(Main.getInstance()).getString("updateChecker.noUpdate"));
                updateAvailable = false;
            } else {
                getLogger().info(EasyLanguages.GetServerLanguage(Main.getInstance()).getString("updateChecker.update"));
                updateAvailable = true;

            }
        });

        // Plugin startup logic
        timer = new Timer(false, 0, false, true);

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

        EasyLanguages.SetDefaultTranslation(this, "en_us");
        EasyLanguages.SetDefaultTranslation(this, "de_de");

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

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new TimerExpansion().register();
        }
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

    public List<Player> getToggledActionbarPlayers() {

        return toggledActionbarPlayers;
    }

    public void setToggledActionbarPlayers(List<Player>inputToggledActionbarPlayers) {

        toggledActionbarPlayers = inputToggledActionbarPlayers;
    }
}
