package de.enderkatze.katzcrafttimer.core.timer

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.KatzcraftTimer
import de.enderkatze.katzcrafttimer.api.framework.timer.Timer
import de.enderkatze.katzcrafttimer.api.framework.timer.TimerManager
import de.enderkatze.katzcrafttimer.api.events.TimerUpdateEvent
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.scheduler.BukkitTask
import java.util.UUID

class TimerNormal
@Inject constructor(
    private val plugin: KatzcraftTimer,
    private val timerManager: TimerManager
): Timer {

    override var time: Int = 0
    override val id: String = UUID.randomUUID().toString()
    override val type: String = "normal"
    override var running: Boolean = false

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
            task?.cancel()
            task = null
        }
    }

    override fun toMap(): Map<String, Any?> {
        return mapOf(
            id to mapOf(
            "time" to time,
            "running" to running,
            "isPrimary" to (this.timerManager.getPrimaryTimer()?.equals(this) ?: false),
            )
        )
    }

    override fun isPrimaryTimer(): Boolean {
        return this == timerManager.getPrimaryTimer()
    }


    private fun runTimer() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            if (running) {
                time++
                val event: Event = TimerUpdateEvent(time, this)
                Bukkit.getServer().pluginManager.callEvent(event)
            }
        }, 0L, 20L)
    }

    override fun copy() {
        TODO("Not yet implemented")
    }

}