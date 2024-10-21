package de.enderkatze.katzcrafttimer.api

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.KatzcraftTimer
import de.enderkatze.katzcrafttimer.api.framework.timer.TimerManager

class KatzcraftTimerAPI private constructor(
    private val plugin: KatzcraftTimer,
    private val timerManager: TimerManager,
) {
    companion object {
        @Volatile
        private var instance: KatzcraftTimerAPI? = null

        @JvmStatic
        internal fun initialize(plugin: KatzcraftTimer, timerManager: TimerManager) {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = KatzcraftTimerAPI(plugin, timerManager)
                    }
                }
            }
        }

        @JvmStatic
        fun getTimerManager(): TimerManager {
            return getInstance().timerManager
        }

        private fun getInstance(): KatzcraftTimerAPI {
            return instance ?: throw IllegalStateException("KatzcraftTimerAPI has not been initialized by the plugin")
        }
    }

}
