package de.enderkatze.katzcrafttimer.domain.contracts.data

import de.enderkatze.katzcrafttimer.presenter.display.PausedDisplayType
import de.enderkatze.katzcrafttimer.presenter.display.TimerDisplayType
import org.bukkit.entity.Player

data class PlayerSetting(
    val player: Player,
    var timerDisplayType: TimerDisplayType,
    var pauseDisplayType: PausedDisplayType
)