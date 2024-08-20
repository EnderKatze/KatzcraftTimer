package de.enderkatze.katzcrafttimer.timer

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.Main
import de.enderkatze.katzcrafttimer.events.TimerUpdateEvent
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.scheduler.BukkitTask
import java.util.UUID

class TimerNormal
@Inject constructor(
    private val plugin: Main,
    private val timerManager: TimerManager
): Timer {

    override var time: Int = 0
    override val id: String = UUID.randomUUID().toString()
    override val timerType: TimerType = TimerType.NORMAL

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
            "timerType" to timerType
        )
    }

    override fun isPrimaryTimer(): Boolean {
        return this == timerManager.getPrimaryTimer()
    }


    private fun runTimer() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            if (running) {
                time++
                val event: Event = TimerUpdateEvent(time, isPrimaryTimer())
                Bukkit.getServer().pluginManager.callEvent(event)
            }
        }, 0L, 20L)
    }

}