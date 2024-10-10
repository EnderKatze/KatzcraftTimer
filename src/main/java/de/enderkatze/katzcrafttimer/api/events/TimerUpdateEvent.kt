package de.enderkatze.katzcrafttimer.api.events

import de.enderkatze.katzcrafttimer.api.framework.timer.Timer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class TimerUpdateEvent(val time: Int, val isPrimaryTimer: Boolean, val timer: Timer) : Event(){

    companion object {

        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }

    override fun getHandlers(): HandlerList {
        return getHandlerList()
    }

}