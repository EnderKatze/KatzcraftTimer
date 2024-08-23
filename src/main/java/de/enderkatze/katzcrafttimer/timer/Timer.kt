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


    companion object {
        private val registry = mutableMapOf<TimerType, (Map<String, Any?>) -> Timer>()

        fun fromMap(map: Map<String, Any?>): Timer {
            val timerType = map["timerType"] as? TimerType
                ?: throw IllegalArgumentException("timerType is missing or invalid")
            val fromMapFunction = registry[timerType]
                ?: throw IllegalArgumentException("No registered Timer for timerType: $timerType")
            return fromMapFunction(map)
        }
    }
}