package de.enderkatze.katzcrafttimer.timer

interface TimerFactory {

    fun createNormalTimer(): Timer
    fun createCountdownTimer(): Timer
}