package de.enderkatze.katzcrafttimer.infra.data.playerdata

import de.enderkatze.katzcrafttimer.core.presenter.display.PausedDisplayType
import de.enderkatze.katzcrafttimer.core.presenter.display.TimerDisplayType
import org.bukkit.entity.Player

data class PlayerSetting(
    val player: Player,
    val timerDisplayType: TimerDisplayType,
    val pauseDisplayType: PausedDisplayType
) {

}