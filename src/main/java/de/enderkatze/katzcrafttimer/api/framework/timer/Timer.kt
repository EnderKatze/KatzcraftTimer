package de.enderkatze.katzcrafttimer.api.framework.timer

interface Timer {

    var time: Int
    val id: String


    fun start()
    fun stop()
    fun isRunning(): Boolean
    fun toMap(): Map<String, Any?>

    fun copy()

    fun reset() {
        time = 0
        stop()
    }

    fun isPrimaryTimer(): Boolean;

}