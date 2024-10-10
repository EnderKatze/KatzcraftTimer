package de.enderkatze.katzcrafttimer.core.presenter.timer_display

import de.enderkatze.katzcrafttimer.api.events.TimerUpdateEvent
import org.bukkit.event.Event
import org.bukkit.event.Listener

interface TimerDisplay{

    fun displayTime(event: TimerUpdateEvent)
}