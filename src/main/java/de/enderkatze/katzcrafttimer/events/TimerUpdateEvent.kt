package de.enderkatze.katzcrafttimer.events

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class TimerUpdateEvent(val time: Int, val isPrimaryTimer: Boolean) : Event(){

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