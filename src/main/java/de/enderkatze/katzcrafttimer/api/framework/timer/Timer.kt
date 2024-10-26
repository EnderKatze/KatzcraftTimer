package de.enderkatze.katzcrafttimer.api.framework.timer

interface Timer {

    var time: Int
    val id: String
    val type: String
    var running: Boolean


    fun start()
    fun stop()
    fun toMap(): Map<String, Any?>

    fun copy(): Timer

    fun reset() {
        time = 0
        stop()
    }

    fun isPrimaryTimer(): Boolean;

}