package de.enderkatze.katzcrafttimer;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.enderkatze.katzcrafttimer.commands.subcommands.*;
import de.enderkatze.katzcrafttimer.core.framework.MainBinderModule;
import de.enderkatze.katzcrafttimer.listeners.CountdownEndListener;
import de.enderkatze.katzcrafttimer.timer.TimerFactory;
import de.enderkatze.katzcrafttimer.timer.TimerManager;
import de.enderkatze.katzcrafttimer.utitlity.LanguageHandler;
import de.enderkatze.katzcrafttimer.utitlity.Metrics;
import de.enderkatze.katzcrafttimer.utitlity.TimerExpansion;
import de.enderkatze.katzcrafttimer.utitlity.UpdateChecker;
import de.enderkatze.katzcrafttimer.commands.TimerCommand;
import de.enderkatze.katzcrafttimer.listeners.PlayerJoinListener;
import de.enderkatze.katzcrafttimer.timer.deprecated.TimerOld;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    public boolean updateAvailable = false;

    @Getter
    private String newestVersion;

    @Getter private static Main instance;

    @Inject private TimerFactory timerFactory;
    @Inject private TimerManager timerManager;

    private TimerOld timer;

    @Inject private TimerCommand timerCommand;

    @Setter
    @Getter
    private List<Player> toggledActionbarPlayers = new ArrayList<>();

    @Getter private NamespacedKey hologramKey;

    private final File data = new File(this.getDataFolder(), "data.yml");
    private final FileConfiguration dataConfig = YamlConfiguration.loadConfiguration(data);

    @Override
    public void onLoad() {
        instance = this;
        hologramKey = new NamespacedKey(Main.getInstance(), "timerHologram");

        MainBinderModule module = new MainBinderModule(this);
        Injector injector = Guice.createInjector(module);
        injector.injectMembers(this);

    }

    private void saveData() {
        try{

            dataConfig.save(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {

        newestVersion = this.getDescription().getVersion();

        // Check for updates and send messages
        new UpdateChecker(this, 104380).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info(getLanguage().getString("updateChecker.noUpdate"));
                updateAvailable = false;
            } else {
                String updateMessage = getLanguage().getString("updateChecker.update");
                newestVersion = version;
                updateMessage = updateMessage.replace("{newVer}", newestVersion);
                getLogger().info(updateMessage);
                updateAvailable = true;

            }
        });

        // Does nothing if there is already data
        InitialiseData();
        setupTimer();

        // Register events
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new CountdownEndListener(), this);

        saveDefaultConfig();
        reloadConfig();

        // Register timer command and subcommands

        timerCommand.registerSubcommand(new CountdownCommand());
        timerCommand.registerSubcommand(new HologramCommand());
        timerCommand.registerSubcommand(new PauseCommand());
        timerCommand.registerSubcommand(new ReloadCommand());
        timerCommand.registerSubcommand(new ResetCommand());
        timerCommand.registerSubcommand(new ResumeCommand());
        timerCommand.registerSubcommand(new TimeCommand());
        timerCommand.registerSubcommand(new ToggleDisplayCommand());

        getCommand("timer").setExecutor(timerCommand);
        getCommand("timer").setTabCompleter(timerCommand);

        // PlaceholderAPI hook
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new TimerExpansion().register();
        }

        Metrics metrics = new Metrics(this, 	20951);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        timer.removeAllPlayersFromBossBar();

        if(getConfig().getBoolean("saveTime")) {
            dataConfig.set("time", timer.getTime());
        }

        dataConfig.set("currentlyBackwards", timer.getBackwards());

        saveData();


    }


    public TimerOld getTimer() {
        if(timer == null) {
            setupTimer();
        }
        return timer;
    }

    public String getPrefix() {
        String text = getConfig().getString("prefix");
        return "[" + text + ChatColor.RESET + "] ";
    }

    public FileConfiguration getLanguage() {
        return LanguageHandler.getInstance().getSelectedLanguage();
    }

    private void setupTimer() {

        timer = new TimerOld(false, 0, false, true, getConfig().getBoolean("displayTimeOnPause"));



        if(getConfig().getBoolean("saveTime")) {
            timer.setTime(dataConfig.getInt("time"));
        }

        if(!getConfig().getBoolean("pauseOnRestart")) {
            timer.setRunning(true);
        }

        timer.setBackwards(dataConfig.getBoolean("currentlyBackwards"));

    }

    private void InitialiseData() {

        if(!data.exists()) {
            Bukkit.getLogger().log(Level.INFO, "Data file doesn't exist, creating one");
            Bukkit.getLogger().log(Level.FINEST, "Created data file");
            saveData();

        }

        if(!dataConfig.contains("time")) {

            dataConfig.set("time", 0);

        }
        if(!dataConfig.contains("currentlyBackwards")) {

            dataConfig.set("currentlyBackwards", false);
        }
    }
}
