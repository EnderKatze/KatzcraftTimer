package de.enderkatze.katzcrafttimer.presenter.timer_display

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.Main
import de.enderkatze.katzcrafttimer.events.TimerUpdateEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventHandler

class DefaultTimerDisplay @Inject constructor(private val plugin: Main) : TimerDisplay {

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