package de.enderkatze.katzcrafttimer.domain.contracts.timer

interface TimerManager {
    fun addTimer(timer: Timer)
    fun removeTimer(timer: Timer)
    fun getPrimaryTimer(): Timer?
    fun getTimer(id: String): Timer?
    fun getTimers(): List<Timer>
    fun setPrimaryTimer(index: Int)
    fun startAll()
    fun stopAll()
    fun resetAll()
}