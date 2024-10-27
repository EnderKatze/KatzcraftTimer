package de.enderkatze.katzcrafttimer

import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.name.Named
import de.enderkatze.katzcrafttimer.api.KatzcraftTimerAPI
import de.enderkatze.katzcrafttimer.api.framework.timer.Timer
import de.enderkatze.katzcrafttimer.api.framework.timer.TimerManager
import de.enderkatze.katzcrafttimer.domain.logic.modules.MainBinderModule
import de.enderkatze.katzcrafttimer.core.commands.TimerCommand
import de.enderkatze.katzcrafttimer.core.commands.subcommands.*
import de.enderkatze.katzcrafttimer.core.listeners.CountdownEndListener
import de.enderkatze.katzcrafttimer.core.listeners.PlayerJoinListener
import de.enderkatze.katzcrafttimer.core.listeners.TimerUpdateListener
import de.enderkatze.katzcrafttimer.core.timer.deprecated.TimerOld
import de.enderkatze.katzcrafttimer.core.utitlity.LanguageHandler
import de.enderkatze.katzcrafttimer.core.utitlity.Metrics
import de.enderkatze.katzcrafttimer.external.placeholderapi.TimerExpansion
import de.enderkatze.katzcrafttimer.core.utitlity.UpdateChecker
import lombok.Getter
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.util.logging.Level

class KatzcraftTimer : JavaPlugin() {
    @JvmField
    var updateAvailable: Boolean = false

    var newestVersion: String? = null
        private set

    @Inject
    private lateinit var timerManager: TimerManager

    private var timer: TimerOld? = null

    @Inject
    @Named("timer.normal")
    private lateinit var timerNormal: Timer

    @Inject
    private lateinit var timerCommand: TimerCommand

    @Inject
    private lateinit var countdownEndListener: CountdownEndListener

    @Inject
    private lateinit var timerUpdateListener: TimerUpdateListener

    @Inject private lateinit var timerExpansion: TimerExpansion

    var toggledActionbarPlayers: List<Player> = ArrayList()

    @Getter
    private var hologramKey: NamespacedKey? = null

    private val data = File(this.dataFolder, "data.yml")
    private val dataConfig: FileConfiguration = YamlConfiguration.loadConfiguration(data)

    override fun onLoad() {
        instance = this

        val module = MainBinderModule(this)
        val injector = Guice.createInjector(module)
        injector.injectMembers(this)

        hologramKey = NamespacedKey(this, "timerHologram")

        KatzcraftTimerAPI.initialize(this, timerManager)
    }

    private fun saveData() {
        try {
            dataConfig.save(data)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onEnable() {
        newestVersion = description.version

        // Check for updates and send messages
        UpdateChecker(this, 104380).getVersion { version: String ->
            if (description.version == version) {
                logger.info(language.getString("updateChecker.noUpdate"))
                updateAvailable = false
            } else {
                var updateMessage = language.getString("updateChecker.update")!!
                newestVersion = version
                updateMessage = updateMessage.replace("{newVer}", newestVersion!!)
                logger.info(updateMessage)
                updateAvailable = true
            }
        }

        // Does nothing if there is already data
        initData()
        setupTimer()

        // Register events
        Bukkit.getPluginManager().registerEvents(PlayerJoinListener(), this)
        Bukkit.getPluginManager().registerEvents(countdownEndListener, this)
        Bukkit.getPluginManager().registerEvents(timerUpdateListener, this)

        // Register timer command and subcommands
        timerCommand.registerSubcommand(CountdownCommand())
        timerCommand.registerSubcommand(HologramCommand())
        timerCommand.registerSubcommand(PauseCommand())
        timerCommand.registerSubcommand(ReloadCommand())
        timerCommand.registerSubcommand(ResetCommand())
        timerCommand.registerSubcommand(ResumeCommand())
        timerCommand.registerSubcommand(TimeCommand())
        timerCommand.registerSubcommand(ToggleDisplayCommand())

        getCommand("timer")!!.setExecutor(timerCommand)
        getCommand("timer")!!.tabCompleter = timerCommand

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            timerExpansion.register()
        }

        val metrics = Metrics(this, 20951)
    }

    override fun onDisable() {
        // Plugin shutdown logic



        timer!!.removeAllPlayersFromBossBar()

        if (config.getBoolean("saveTime")) {
            dataConfig["time"] = timer!!.time
        }

        dataConfig["currentlyBackwards"] = timer!!.backwards

        saveData()
    }


    fun getTimer(): TimerOld? {
        if (timer == null) {
            setupTimer()
        }
        return timer
    }

    val prefix: String
        get() {
            val text = config.getString("prefix")
            return "[" + text + ChatColor.RESET + "] "
        }

    val language: FileConfiguration
        get() = LanguageHandler.getInstance(this).selectedLanguage

    private fun setupTimer() {
        timer = TimerOld(false, 0, false, true, config.getBoolean("displayTimeOnPause"))

        if (config.getBoolean("saveTime")) {
            timer!!.setTime(dataConfig.getInt("time"))
        }

        if (!config.getBoolean("pauseOnRestart")) {
            timer!!.isRunning = true
        }

        timer!!.isBackwards = dataConfig.getBoolean("currentlyBackwards")
    }

    private fun initData() {
        saveDefaultConfig()
        reloadConfig()

        if (!data.exists()) {
            Bukkit.getLogger().log(Level.INFO, "Data file doesn't exist, creating one")
            Bukkit.getLogger().log(Level.FINEST, "Created data file")
            saveData()
        }

        if (!dataConfig.contains("time")) {
            dataConfig["time"] = 0
        }
        if (!dataConfig.contains("currentlyBackwards")) {
            dataConfig["currentlyBackwards"] = false
        }
    }

    companion object {
        @Getter
        private var instance: KatzcraftTimer? = null
    }
}
