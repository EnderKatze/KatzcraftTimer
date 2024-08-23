package de.enderkatze.katzcrafttimer.core.data.config.timer_config

import de.enderkatze.katzcrafttimer.core.data.config.CustomConfig
import de.enderkatze.katzcrafttimer.timer.Timer

interface TimerConfig: CustomConfig {

    fun loadTimers(): List<Timer>

    fun saveTimers()
}