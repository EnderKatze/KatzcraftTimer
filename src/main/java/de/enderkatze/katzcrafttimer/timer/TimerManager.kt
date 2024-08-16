package de.enderkatze.katzcrafttimer.timer

interface TimerManager {

    interface TimerManager {
        fun addTimer(timer: Timer)
        fun removeTimer(timer: Timer)
        fun getPrimaryTimer(): Timer?
        fun setPrimaryTimer(index: Int)
        fun startAll()
        fun stopAll()
        fun resetAll()
    }
}