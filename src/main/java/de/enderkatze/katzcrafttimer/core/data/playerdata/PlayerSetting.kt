package de.enderkatze.katzcrafttimer.core.data.playerdata

import de.enderkatze.katzcrafttimer.core.presenter.timer_display.PausedDisplayType
import de.enderkatze.katzcrafttimer.core.presenter.timer_display.TimerDisplayType
import org.bukkit.entity.Player

data class PlayerSetting(
    val player: Player,
    val timerDisplayType: TimerDisplayType,
    val pauseDisplayType: PausedDisplayType
) {

}