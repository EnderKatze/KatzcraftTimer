package de.enderkatze.katzcrafttimer.timer

interface Timer {

    var time: Int
    val id: String
    val timerType: TimerType

    fun start()
    fun stop()
    fun isRunning(): Boolean
    fun toMap(): Map<String, Any?>

    fun reset() {
        time = 0
        stop()
    }

    fun isPrimaryTimer(): Boolean;

}