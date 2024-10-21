package de.enderkatze.katzcrafttimer.core.presenter.display.display_handlers

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.KatzcraftTimer
import de.enderkatze.katzcrafttimer.domain.contracts.data.SettingsManager
import de.enderkatze.katzcrafttimer.api.framework.timer.Timer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player

class BossbarDisplayHandler @Inject constructor(
    private val plugin: KatzcraftTimer,
    private val settingsManager: SettingsManager,
) {

    var bossBar: BossBar? = null

    init {
        bossBar = Bukkit.createBossBar(
            "",
            BarColor.valueOf(plugin.config.getString("bossBarColor")!!),
            BarStyle.SOLID)
    }

    fun display(player: Player, timer: Timer) {
        val pausedDisplaySetting = settingsManager.getSetting(player)!!.pauseDisplayType

        if (!bossBar!!.players.contains(player)) {
            bossBar!!.addPlayer(player)
        }

        if(timer.isRunning()) {
            bossBar!!.setTitle(
                ChatColor.valueOf(plugin.config.getString("timerColor")!!).toString()
                        + ChatColor.BOLD
                        + timer.time)
        }

        // TODO Find better solution for this
        bossBar!!.players.remove(player)
    }
}