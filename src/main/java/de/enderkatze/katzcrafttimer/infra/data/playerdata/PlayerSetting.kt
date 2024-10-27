package de.enderkatze.katzcrafttimer.infra.data.playerdata

import de.enderkatze.katzcrafttimer.presenter.display.PausedDisplayType
import de.enderkatze.katzcrafttimer.presenter.display.TimerDisplayType
import org.bukkit.entity.Player

data class PlayerSetting(
    val player: Player,
    val timerDisplayType: TimerDisplayType,
    val pauseDisplayType: PausedDisplayType
) {
    fun toMap(): Map<String, Map<String, Any>> {
        return mapOf(
            player.uniqueId.toString() to mapOf(
                "timerDisplayType" to timerDisplayType,
                "pausedDisplayType" to pauseDisplayType
            )
        )
    }
}