package de.enderkatze.katzcrafttimer.infra.timer

import de.enderkatze.katzcrafttimer.domain.contracts.timer.Timer
import de.enderkatze.katzcrafttimer.domain.contracts.timer.TimerManager

class TimerManagerImpl: TimerManager {

    private val timers: MutableList<Timer> = arrayListOf()
    var primaryTimerIndex: Int = 0
        private set

    override fun addTimer(timer: Timer) {
        timers.add(timer)
    }

    override fun removeTimer(timer: Timer) {
        timers.remove(timer)
    }

    override fun getPrimaryTimer(): Timer? {
        return timers.getOrNull(primaryTimerIndex)
    }

    override fun getTimer(id: String): Timer? {
        return timers.firstOrNull { it.id == id }
    }

    override fun getTimers(): List<Timer> {
        return timers
    }

    override fun setPrimaryTimer(index: Int) {
        if (index in timers.indices) {
            primaryTimerIndex = index
        } else {
            throw IndexOutOfBoundsException()
        }
    }

    override fun startAll() {
        for (timer: Timer in timers) {
            timer.start()
        }
    }

    override fun stopAll() {
        for (timer: Timer in timers) {
            timer.stop()
            var timerData: Map<String, Any?>  = timer.toMap()
        }
    }

    override fun resetAll() {
        for (timer: Timer in timers) {
            timer.reset()
        }
    }
}