package de.enderkatze.katzcrafttimer.timer

class DefaultTimerManager: TimerManager{

    private val timers: MutableList<Timer> = arrayListOf()
    var primaryTimerIndex: Int = 0

    override fun addTimer(timer: Timer) {
        timers.add(timer)
    }

    override fun removeTimer(timer: Timer) {
        timers.remove(timer)
    }

    override fun getPrimaryTimer(): Timer? {
        return timers.getOrNull(primaryTimerIndex)
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

    override fun stopAllAndSave() {
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