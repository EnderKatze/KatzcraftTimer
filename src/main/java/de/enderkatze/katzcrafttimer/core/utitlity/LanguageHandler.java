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

    private LanguageHandler() {

        String language = KatzcraftTimer.getInstance().getConfig().getString("language");
        try {

            File languageFile = new File(KatzcraftTimer.getInstance().getDataFolder(), "languages/" + language + ".yml");

            if (!languageFile.exists()) {
                KatzcraftTimer.getInstance().saveResource("languages/" + language + ".yml", false);
            }

            languageConfig = YamlConfiguration.loadConfiguration(languageFile);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error loading language file:", e);
        }
    }

    public static LanguageHandler getInstance() {
        if(instance==null) {
            instance = new LanguageHandler();
        }

        return instance;
    }

    public FileConfiguration getSelectedLanguage() {
        return this.languageConfig;
    }
}
