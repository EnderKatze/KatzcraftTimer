package de.enderkatze.katzcrafttimer.core.framework.data

data class TimerData(
    val time: Int,
    val running: Boolean,
    val id: String,
    val type: String,
    val special: Map<String, Any>,
)
