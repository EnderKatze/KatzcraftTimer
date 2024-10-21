package de.enderkatze.katzcrafttimer.domain.contracts.data

import de.enderkatze.katzcrafttimer.infra.data.playerdata.PlayerSetting
import org.bukkit.entity.Player

interface SettingsManager {

    fun getSetting(player: Player): PlayerSetting?
}