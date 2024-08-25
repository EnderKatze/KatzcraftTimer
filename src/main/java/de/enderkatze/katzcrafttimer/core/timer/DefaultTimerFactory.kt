package de.enderkatze.katzcrafttimer.core.timer

import com.google.inject.Inject
import com.google.inject.Provider
import de.enderkatze.katzcrafttimer.api.framework.timer.Timer
import de.enderkatze.katzcrafttimer.core.framework.timer.TimerFactory

class DefaultTimerFactory @Inject constructor(
    private val timerNormalProvider: Provider<TimerNormal>
): TimerFactory {
    override fun createNormalTimer(): Timer {
        return timerNormalProvider.get()
    }

    override fun createCountdownTimer(): Timer {
        TODO("Not yet implemented")
    }
}