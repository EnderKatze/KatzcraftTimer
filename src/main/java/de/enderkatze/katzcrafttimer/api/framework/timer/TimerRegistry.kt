package de.enderkatze.katzcrafttimer.api.framework.timer

interface TimerRegistry {

    fun registerTimerType(type: String, timerFactory: () -> Timer)

    fun getTimerTypes(): Map<String, () -> Timer>

    fun getTimerFromType(type: String): Timer?
}