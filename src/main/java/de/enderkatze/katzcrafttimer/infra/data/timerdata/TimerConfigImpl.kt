package de.enderkatze.katzcrafttimer.infra.data.timerdata

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.KatzcraftTimer
import de.enderkatze.katzcrafttimer.core.framework.data.TimerConfig
import de.enderkatze.katzcrafttimer.api.framework.timer.Timer
import de.enderkatze.katzcrafttimer.api.framework.timer.TimerManager
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class TimerConfigImpl @Inject constructor(
    private val plugin: KatzcraftTimer,
    private val configFile: File = File(plugin.dataFolder, "data/timers.yml"),
    private val timerManager: TimerManager
) : TimerConfig {

    private var config: FileConfiguration? = null

    init {
        if (!configFile.parentFile.exists()) {
            configFile.parentFile.mkdirs()
        }
        reloadConfig()
    }

    override fun loadTimers(): List<Timer> {
        val timers = mutableListOf<Timer>()
        val timerSection = getConfig()?.getConfigurationSection("timers")
        if (timerSection != null) {
            for (key in timerSection.getKeys(false)) {
                val timerMap = timerSection.getConfigurationSection(key)?.getValues(false)
                if (timerMap != null) {
                    // TODO Implement adding timer from map
                    // TODO add timer to timerManager object
                }
            }
        }
        return timers
    }

    override fun saveTimers() {
        for(timer: Timer in timerManager.getTimers()) {
            getConfig()!!.set("timers.${timer.id}", timer.toMap())
        }
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
