package de.enderkatze.katzcrafttimer.infra.data.playerdata

import de.enderkatze.katzcrafttimer.domain.contracts.data.SettingsManager
import de.enderkatze.katzcrafttimer.core.framework.data.GlobalDataConfig
import org.bukkit.entity.Player
import javax.inject.Inject

class SettingsManagerImpl @Inject constructor(
    val dataConfig: GlobalDataConfig
): SettingsManager {
    override fun getSetting(player: Player): PlayerSetting? {
        TODO("Not yet implemented")
    }


}