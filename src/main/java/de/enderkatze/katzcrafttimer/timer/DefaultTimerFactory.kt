package de.enderkatze.katzcrafttimer.timer

import com.google.inject.Inject
import com.google.inject.Provider

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