package de.enderkatze.katzcrafttimer.infra.data.playerdata

import de.enderkatze.katzcrafttimer.domain.contracts.data.PlayerSettingsManager
import de.enderkatze.katzcrafttimer.core.framework.data.GlobalDataConfig
import de.enderkatze.katzcrafttimer.presenter.display.PausedDisplayType
import de.enderkatze.katzcrafttimer.presenter.display.TimerDisplayType
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import java.util.*
import javax.inject.Inject


class PlayerSettingsManagerImpl @Inject constructor(
    val dataConfig: GlobalDataConfig
): PlayerSettingsManager {

    private var settings: MutableList<PlayerSetting> = mutableListOf<PlayerSetting>()

    override fun loadSettings() {
        val configSection: ConfigurationSection = dataConfig
            .getConfig()
            ?.getConfigurationSection("players")
            ?: return

        for (key in configSection.getKeys(false)) {

            val uuid = try {
                UUID.fromString(key)
            } catch (e: IllegalArgumentException) {

                continue
            }

            val player: Player = Bukkit.getPlayer(uuid) ?: continue


            val timerDisplayTypeStr = configSection.getString("timerDisplayType")
            val pausedDisplayTypeStr = configSection.getString("pausedDisplayType")

            val timerDisplayType = try {
                timerDisplayTypeStr?.let { TimerDisplayType.valueOf(it) }
            } catch (e: IllegalArgumentException) {
                null
            }

            val pausedDisplayType = try {
                pausedDisplayTypeStr?.let { PausedDisplayType.valueOf(it) }
            } catch (e: IllegalArgumentException) {
                null
            }

            if(timerDisplayType != null && pausedDisplayType != null) {
                settings.add(PlayerSetting(player, timerDisplayType, pausedDisplayType))
            }

        }
    }

    override fun getSetting(player: Player): PlayerSetting? {
        val existingSetting = settings.find { it.player.uniqueId == player.uniqueId }

        if (existingSetting != null) {
            return existingSetting
        }

        // Define default settings here
        val defaultSetting = PlayerSetting(
            player = player,
            timerDisplayType = TimerDisplayType.ACTIONBAR,
            pauseDisplayType = PausedDisplayType.TEXT,
        )

        // Add the new default setting to the list
        settings.add(defaultSetting)

        return defaultSetting
    }

    override fun saveSettings() {
        val config = dataConfig.getConfig() ?: return
        val playersSection = config.createSection("players")

        for (setting in settings) {
            val playerSection = playersSection.createSection(setting.player.uniqueId.toString())
            playerSection.set("timerDisplayType", setting.timerDisplayType.name)
            playerSection.set("pausedDisplayType", setting.pauseDisplayType.name)
        }

        dataConfig.saveConfig()
    }

    override fun saveSetting(player: Player) {
        val setting = getSetting(player) ?: return
        val config = dataConfig.getConfig() ?: return
        val playerSection = config.getConfigurationSection("players")
            ?.createSection(player.uniqueId.toString()) ?: return

        playerSection.set("timerDisplayType", setting.timerDisplayType.name)
        playerSection.set("pausedDisplayType", setting.pauseDisplayType.name)

        dataConfig.saveConfig()
    }


}