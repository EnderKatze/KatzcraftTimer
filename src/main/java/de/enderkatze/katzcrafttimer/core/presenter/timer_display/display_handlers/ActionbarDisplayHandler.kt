package de.enderkatze.katzcrafttimer.core.presenter.timer_display.display_handlers

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.KatzcraftTimer
import de.enderkatze.katzcrafttimer.api.framework.data.playerdata.SettingsManager
import de.enderkatze.katzcrafttimer.api.framework.timer.Timer
import de.enderkatze.katzcrafttimer.api.framework.timer.TimerManager
import de.enderkatze.katzcrafttimer.core.presenter.timer_display.PausedDisplayType
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class ActionbarDisplayHandler @Inject constructor(
    private val settingsManager: SettingsManager,
    private val plugin: KatzcraftTimer,
) {
    fun display(player: Player, primaryTimer: Timer) {
        val pausedDisplaySetting = settingsManager.getSetting(player)!!.pauseDisplayType

        if(primaryTimer.isRunning()) {
            player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                TextComponent(
                ChatColor
                    .valueOf(
                        plugin.config.getString("timerColor")!!).toString() +
                        ChatColor.BOLD +
                        primaryTimer.time))
        } else {

            var message: String = ChatColor.valueOf(plugin.config.getString("timerColor")!!).toString() +
                    ChatColor.ITALIC + ChatColor.BOLD
            when (pausedDisplaySetting) {
                PausedDisplayType.MESSAGE -> {
                    message += primaryTimer.time
                }
                PausedDisplayType.TEXT -> {
                    message += plugin.language.getString("actionbarPausedMessage")
                }
                PausedDisplayType.HIDE -> {
                    message = ""
                }
            }

            if(message.isNotBlank()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(message))
            }
        }
    }

}