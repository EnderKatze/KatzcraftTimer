package de.enderkatze.katzcrafttimer.core.framework.data

import de.enderkatze.katzcrafttimer.api.framework.timer.Timer

interface TimerConfig: CustomConfig {

    fun loadTimers(): List<Timer>

    fun saveTimers()
}