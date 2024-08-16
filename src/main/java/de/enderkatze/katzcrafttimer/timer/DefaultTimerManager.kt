package de.enderkatze.katzcrafttimer.timer

object DefaultTimerManager {
    private val timers: MutableList<Timer> = arrayListOf()
    var primaryTimerIndex: Int = 0

    fun addTimer(timer: Timer) {
        timers.add(timer)
    }

    fun removeTimer(timer: Timer) {
        timers.remove(timer)
    }

    fun getPrimaryTimer(): Timer? {
        return timers.getOrNull(primaryTimerIndex)
    }

    fun setPrimaryTimerIndex(index: Int) {
        if (index in timers.indices) {
            primaryTimerIndex = index
        } else {
            throw IndexOutOfBoundsException()
        }
    }

    fun startAll() {
        for (timer: Timer in timers) {
            timer.running = true
        }
    }

    fun stopAll() {
        for (timer: Timer in timers) {
            timer.running = false
        }
    }

    fun resetAll() {
        for (timer: Timer in timers) {
            timer.reset()
        }
    }
}