package de.enderkatze.katzcrafttimer.core.framework.timer

import de.enderkatze.katzcrafttimer.api.framework.timer.Timer

interface TimerFactory {

    fun createNormalTimer(): Timer
    fun createCountdownTimer(): Timer
}