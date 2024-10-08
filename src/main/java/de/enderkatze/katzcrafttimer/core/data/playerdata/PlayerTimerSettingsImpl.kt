package de.enderkatze.katzcrafttimer.core.data.playerdata

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.KatzcraftTimer
import de.enderkatze.katzcrafttimer.api.framework.data.PlayerTimerSettings
import org.bukkit.entity.Player

class PlayerTimerSettingsImpl @Inject constructor(
    val player: Player,
    // These under here need to be injected with guice
    val plugin: KatzcraftTimer,
    ): PlayerTimerSettings {
}