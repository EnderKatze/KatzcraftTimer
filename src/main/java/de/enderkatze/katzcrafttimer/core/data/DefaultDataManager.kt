package de.enderkatze.katzcrafttimer.core.data

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.core.data.config.GlobalDataConfig
import de.enderkatze.katzcrafttimer.core.data.config.TimerConfig
import de.enderkatze.katzcrafttimer.timer.Timer
import de.enderkatze.katzcrafttimer.timer.TimerManager

class DefaultDataManager @Inject constructor(
    val timerConfig: TimerConfig,
    val globalDataConfig: GlobalDataConfig,
    val timerManager: TimerManager,
): DataManager {
    override fun saveTimers() {
        for (timer: Timer in timerManager.getTimers()) {
            val timerMap: Map<String, Any?> = timer.toMap()
            timerConfig.getConfig()?.set("timers.${timer.id}", timerMap)
        }
        timerConfig.saveConfig()
    }

    override fun loadTimers() {
        TODO("Not yet implemented")
    }


}