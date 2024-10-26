package de.enderkatze.katzcrafttimer.presenter.display

import de.enderkatze.katzcrafttimer.api.events.TimerUpdateEvent

interface TimerDisplay{

    fun displayTime(event: TimerUpdateEvent)
}