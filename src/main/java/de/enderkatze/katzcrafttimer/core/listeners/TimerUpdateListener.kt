package de.enderkatze.katzcrafttimer.core.listeners

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.api.events.TimerUpdateEvent
import de.enderkatze.katzcrafttimer.core.presenter.display.TimerDisplay
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener


class TimerUpdateListener @Inject constructor(
    private val timerDisplay: TimerDisplay,
): Listener {



    @EventHandler
    fun onTimerUpdate(event: TimerUpdateEvent) {
        timerDisplay.displayTime(event)
    }
}