package de.enderkatze.katzcrafttimer;

import de.enderkatze.katzcrafttimer.commands.subcommands.*;
import de.enderkatze.katzcrafttimer.listeners.CountdownEndListener;
import de.enderkatze.katzcrafttimer.utitlity.*;
import de.enderkatze.katzcrafttimer.commands.TimerCommand;
import de.enderkatze.katzcrafttimer.listeners.PlayerJoinListener;
import de.enderkatze.katzcrafttimer.timer.Timer;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    public boolean updateAvailable = false;

    @Getter
    private static Main instance;

    private Timer timer;

    @Getter
    @Setter
    private List<Player> toggledActionbarPlayers = new ArrayList<>();

    @Getter
    private NamespacedKey hologramKey;

    private Metrics metrics;

    @Getter
    private List<Hologram> holograms;
    public void addHologram(Hologram h) {
        holograms.add(h);
        metrics.addCustomChart(new Metrics.SimplePie("holograms_per_server", () -> {return String.valueOf(holograms.size());}));
    }


    private final File data = new File(this.getDataFolder(), "data.yml");
    @NonNull
    @Getter
    private final FileConfiguration dataConfig = YamlConfiguration.loadConfiguration(data);
    @Override
    public void onLoad() {
        instance = this;
        hologramKey = new NamespacedKey(Main.getInstance(), "timerHologram");
    }

    public void saveData() {
        try{

            dataConfig.save(data);
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE ,e.getMessage());
        }
    }

    @Override
    public void onEnable() {

        // Check for updates and send messages
        new UpdateChecker(this, 104380).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info(getLanguage().getString("updateChecker.noUpdate"));
                updateAvailable = false;
            } else {
                getLogger().info(getLanguage().getString("updateChecker.update").replaceAll("\\{newVer}", version));
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
        TimerCommand timerCommand = new TimerCommand();

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

        metrics = new Metrics(this, 	20951);

        loadHolograms();
    }

    private void loadHolograms() {

        ConfigurationSection hologramSection = dataConfig.getConfigurationSection("holograms");

        List<String> uuids = new ArrayList<String>(hologramSection.getKeys(true));

        for(String uuid : uuids) {
            holograms.add(new Hologram(uuid));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if(getConfig().getBoolean("saveTime")) {
            dataConfig.set("time", timer.getTime());
        }

        dataConfig.set("currentlyBackwards", timer.getBackwards());

        saveData();
        saveConfig();

    }

    public Timer getTimer() {
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

        timer = new Timer(false, 0, false, true);



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
