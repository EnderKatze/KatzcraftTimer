package de.enderkatze.katzcrafttimer.core.data.config

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.Main
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class DefaultGlobalDataConfig @Inject constructor(

private val plugin: Main,
private val configFile: File = File(plugin.dataFolder, "data/globaldata.yml")
) : GlobalDataConfig {

    private var config: FileConfiguration? = null

    init {
        if (!configFile.parentFile.exists()) {
            configFile.parentFile.mkdirs()
        }
        reloadConfig()
    }

    override fun getConfig(): FileConfiguration? {
        return config
    }

    override fun saveConfig() {
        try {
            config?.save(configFile)
        } catch (e: IOException) {
            plugin.logger.severe("Could not save config to $configFile: ${e.message}")
        }
    }

    override fun reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile)
    }
}