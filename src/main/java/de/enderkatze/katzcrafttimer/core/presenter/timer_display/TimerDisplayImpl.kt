package de.enderkatze.katzcrafttimer.core.presenter.timer_display

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.Main
import de.enderkatze.katzcrafttimer.api.events.TimerUpdateEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventHandler

class TimerDisplayImpl @Inject constructor(private val plugin: Main) : TimerDisplay {

    override fun displayTime(event: Event) {
        for(player: Player in Bukkit.getOnlinePlayers()) {
            continue
        }
    }

    @EventHandler
    fun onTimerUpdate(event: TimerUpdateEvent) {

        if(event.isPrimaryTimer) {
            displayTime(event)
        }
    }
}