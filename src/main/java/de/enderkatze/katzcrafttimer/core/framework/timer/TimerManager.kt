package de.enderkatze.katzcrafttimer.core.framework.timer

import de.enderkatze.katzcrafttimer.api.framework.timer.Timer

interface TimerManager {
    fun addTimer(timer: Timer)
    fun removeTimer(timer: Timer)
    fun getPrimaryTimer(): Timer?
    fun getTimers(): List<Timer>
    fun setPrimaryTimer(index: Int)
    fun startAll()
    fun stopAllAndSave()
    fun resetAll()
}