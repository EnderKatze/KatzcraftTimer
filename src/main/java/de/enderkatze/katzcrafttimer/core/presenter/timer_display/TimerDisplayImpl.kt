package de.enderkatze.katzcrafttimer.core.presenter.timer_display

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.api.events.TimerUpdateEvent
import de.enderkatze.katzcrafttimer.api.framework.data.playerdata.SettingsManager
import de.enderkatze.katzcrafttimer.core.presenter.timer_display.display_handlers.ActionbarDisplayHandler
import de.enderkatze.katzcrafttimer.core.presenter.timer_display.display_handlers.BossbarDisplayHandler
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class TimerDisplayImpl @Inject constructor(
    private val settingsManager: SettingsManager,
    private val actionbarDisplayHandler: ActionbarDisplayHandler,
    private val bossbarDisplayHandler: BossbarDisplayHandler,
) : TimerDisplay {

    override fun displayTime(event: TimerUpdateEvent) {
        if(event.isPrimaryTimer) {
            for (player: Player in Bukkit.getOnlinePlayers()) {
                val type: TimerDisplayType = settingsManager.getSetting(player)!!.timerDisplayType
                when (type) {
                    TimerDisplayType.ACTIONBAR -> actionbarDisplayHandler.display(player, event.timer)
                    TimerDisplayType.BOSSBAR -> bossbarDisplayHandler.display(player, event.timer)
                    TimerDisplayType.NONE -> {}
                }
            }
        }
    }
}