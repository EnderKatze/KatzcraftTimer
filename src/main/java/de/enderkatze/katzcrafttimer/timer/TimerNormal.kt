package de.enderkatze.katzcrafttimer.timer

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.Main
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

class TimerNormal
@Inject constructor(
    private val plugin: Main,
    private val timerManager: TimerManager
): Timer {

    override var time: Int = 0

    private var running: Boolean = false
    private var task: BukkitTask? = null

    override fun start() {
        if(!running) {
            running = true
            runTimer()
        }
    }

    override fun stop() {
        if(running) {
            running = false
        }
    }

    override fun isRunning(): Boolean {
        return running
    }

    override fun toMap(): Map<String, Any?> {
        return mapOf(
            "time" to time,
            "running" to running,
            "isPrimary" to (this.timerManager.getPrimaryTimer()?.equals(this) ?: false),
        )
    }

    override fun isPrimaryTimer(): Boolean {
        return this == timerManager.getPrimaryTimer()
    }


    private fun runTimer() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            if (running) {
                time++
                if(isPrimaryTimer()) {
                    // HOw should the TimerDisplay be notified of the state of the timer without exposing too much?
                }
            }
        }, 0L, 20L)
    }

}