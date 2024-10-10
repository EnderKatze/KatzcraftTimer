package de.enderkatze.katzcrafttimer.api.framework.data.playerdata

import de.enderkatze.katzcrafttimer.core.data.playerdata.PlayerSetting
import org.bukkit.entity.Player

interface SettingsManager {

    fun getSetting(player: Player): PlayerSetting?
}