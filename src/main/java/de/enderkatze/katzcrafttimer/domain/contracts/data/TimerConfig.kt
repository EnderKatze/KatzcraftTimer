package de.enderkatze.katzcrafttimer.domain.contracts.data

import de.enderkatze.katzcrafttimer.domain.contracts.timer.Timer

interface TimerConfig: CustomConfig {

    fun loadTimers()

    fun saveTimers()
}