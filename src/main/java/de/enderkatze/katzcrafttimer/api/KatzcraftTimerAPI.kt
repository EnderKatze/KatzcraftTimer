package de.enderkatze.katzcrafttimer.api

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.Main
import de.enderkatze.katzcrafttimer.core.framework.timer.TimerFactory
import de.enderkatze.katzcrafttimer.core.framework.timer.TimerManager

class KatzcraftTimerAPI @Inject constructor(
    val plugin: Main,
    val timerManager: TimerManager,
    val timerFactory: TimerFactory,
) {


}