package de.enderkatze.katzcrafttimer.presenter.timer_display

import org.bukkit.event.Event
import org.bukkit.event.Listener

interface TimerDisplay : Listener{

    fun displayTime(event: Event): Unit
}