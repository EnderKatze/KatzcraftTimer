package de.enderkatze.katzcrafttimer.timer

interface Timer {
    var running: Boolean

    var time: Int

    fun reset() {
        time = 0
        running = false
    }

    companion object {
        var timers: MutableList<Timer> = arrayListOf()
        var primaryTimerIndex: Int = 0

        fun getPrimaryTimer(): Timer? {
            return this.timers.getOrNull(primaryTimerIndex)
        }
    }
}