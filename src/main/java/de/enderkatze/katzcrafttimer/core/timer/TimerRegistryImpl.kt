package de.enderkatze.katzcrafttimer.core.timer

import de.enderkatze.katzcrafttimer.api.framework.timer.Timer
import de.enderkatze.katzcrafttimer.api.framework.timer.TimerRegistry

class TimerRegistryImpl: TimerRegistry {

    private var timerTypes: MutableMap<String, () -> Timer> = mutableMapOf()

    override fun registerTimerType(type: String, timerFactory: () -> Timer) {
        if(!timerTypes.containsKey(type)) {
            timerTypes[type] = timerFactory
        }
    }

    override fun getTimerTypes(): Map<String, () -> Timer> {
        return timerTypes
    }

    override fun getTimerFromType(type: String): Timer? {
        return timerTypes[type]?.invoke()
    }
}