package de.enderkatze.katzcrafttimer.presenter.timer_display

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.Main
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class TimerDisplay @Inject constructor(private val plugin: Main) : TimerDisplayInterface {

    override fun displayTime() {
        for(player: Player in Bukkit.getOnlinePlayers()) {
            continue
        }
    }
}