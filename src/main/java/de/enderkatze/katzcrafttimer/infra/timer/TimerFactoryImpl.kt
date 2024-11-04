package de.enderkatze.katzcrafttimer.infra.timer

import com.google.inject.Inject
import com.google.inject.name.Named
import de.enderkatze.katzcrafttimer.domain.contracts.timer.Timer
import de.enderkatze.katzcrafttimer.domain.contracts.timer.TimerFactory

class TimerFactoryImpl @Inject constructor(
    @Named("timer.normal") val timerNormal: Timer
): TimerFactory {

    override fun fromType(type: String): Timer? {

        if(type == "normal") {
            return timerNormal
        }
        return null
    }

    override fun fromMap(type: String, map: Map<String, Any>): Timer? {
        TODO("Not yet implemented")
    }
}