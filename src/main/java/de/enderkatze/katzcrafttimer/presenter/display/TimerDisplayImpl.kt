package de.enderkatze.katzcrafttimer.presenter.display

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.api.events.TimerUpdateEvent
import de.enderkatze.katzcrafttimer.domain.contracts.data.PlayerSettingsManager
import de.enderkatze.katzcrafttimer.presenter.display.display_handlers.ActionbarDisplayHandler
import de.enderkatze.katzcrafttimer.presenter.display.display_handlers.BossbarDisplayHandler
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class TimerDisplayImpl @Inject constructor(
    private val playerSettingsManager: PlayerSettingsManager,
    private val actionbarDisplayHandler: ActionbarDisplayHandler,
    private val bossbarDisplayHandler: BossbarDisplayHandler,
) : TimerDisplay {

    override fun displayTime(event: TimerUpdateEvent) {
        if(event.timer.isPrimaryTimer()) {
            for (player: Player in Bukkit.getOnlinePlayers()) {
                val type: TimerDisplayType = playerSettingsManager.getSetting(player)!!.timerDisplayType
                when (type) {
                    TimerDisplayType.ACTIONBAR -> actionbarDisplayHandler.display(player, event.timer)
                    TimerDisplayType.BOSSBAR -> bossbarDisplayHandler.display(player, event.timer)
                    TimerDisplayType.NONE -> {}
                }
            }
        }
    }
}