package de.enderkatze.katzcrafttimer.domain.contracts.timer

interface TimerFactory {

    fun fromType(type: String): Timer?

    fun fromMap(type: String, map: Map<String, Any>): Timer?
}