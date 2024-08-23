package de.enderkatze.katzcrafttimer

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.timer.TimerFactory
import de.enderkatze.katzcrafttimer.timer.TimerManager

class KatzcraftTimerAPI @Inject constructor(
    val plugin: Main,
    val timerManager: TimerManager,
    val timerFactory: TimerFactory,
) {


}