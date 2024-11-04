package de.enderkatze.katzcrafttimer.domain.contracts.data

import org.bukkit.entity.Player

interface PlayerSettingsManager {

    fun getSetting(player: Player): PlayerSetting?
    fun saveSettings()
    fun saveSetting(player: Player)
    fun loadSettings()
}