package de.enderkatze.katzcrafttimer.core.utitlity;

import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

public class LanguageHandler {


    private static LanguageHandler instance;
    private FileConfiguration languageConfig;

    private LanguageHandler(KatzcraftTimer plugin) {

        String language = plugin.getConfig().getString("language");
        try {

            File languageFile = new File(plugin.getDataFolder(), "languages/" + language + ".yml");

            if (!languageFile.exists()) {
                plugin.saveResource("languages/" + language + ".yml", false);
            }

            languageConfig = YamlConfiguration.loadConfiguration(languageFile);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error loading language file:", e);
        }
    }

    public static LanguageHandler getInstance(KatzcraftTimer plugin) {
        if(instance==null) {
            instance = new LanguageHandler(plugin);
        }

        return instance;
    }

    public FileConfiguration getSelectedLanguage() {
        return this.languageConfig;
    }
}
