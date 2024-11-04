package de.enderkatze.katzcrafttimer.presenter.user_interface.commands

import com.google.inject.Inject
import de.enderkatze.katzcrafttimer.domain.contracts.data.PlayerSettingsManager
import de.enderkatze.katzcrafttimer.domain.contracts.timer.Timer
import de.enderkatze.katzcrafttimer.domain.contracts.timer.TimerFactory
import de.enderkatze.katzcrafttimer.domain.contracts.timer.TimerManager
import de.enderkatze.katzcrafttimer.presenter.display.PausedDisplayType
import de.enderkatze.katzcrafttimer.presenter.display.TimerDisplayType
import org.bukkit.entity.Player
import revxrsal.commands.annotation.*
import revxrsal.commands.bukkit.actor.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission


@Command("timer", "katzcrafttimer")
@CommandPermission("katzcrafttimer")
class TimerCommand @Inject constructor(
    private val timerManager: TimerManager,
    private val timerFactory: TimerFactory,
    private val playerSettingsManager: PlayerSettingsManager,
){

    @CommandPlaceholder
    @CommandPermission("katzcrafttimer.control.gui")
    fun openGUI(actor: BukkitCommandActor) {

    }

    @Subcommand("create")
    fun create(actor: BukkitCommandActor, timerType: String, @Optional name: String? = null) {
        timerManager.addTimer(timerFactory.fromType(timerType)!!)
        // TODO Improve
    }

    @Subcommand("pause")
    fun pause(actor: BukkitCommandActor, @Optional timer: Timer = timerManager.getPrimaryTimer()!!) {
        if (timer.running) {
            timer.running = false
        } else {
            actor.sender().sendMessage("PLACEHOLDER.already_paused")
        }
    }

    @Subcommand("reload")
    fun reload(actor: BukkitCommandActor) {
        // TODO Implement
    }

    @Subcommand("reset")
    fun reset(actor: BukkitCommandActor, @Optional timer: Timer = timerManager.getPrimaryTimer()!!) {
        timer.reset()
    }

    @Subcommand("resume")
    fun resume(actor: BukkitCommandActor, @Optional timer: Timer = timerManager.getPrimaryTimer()!!) {
        if (timer.running) {
            timer.running = true
        } else {
            actor.sender().sendMessage("PLACEHOLDER: Already running")
        }
    }

    @Subcommand("time")
    fun time(actor: BukkitCommandActor, @Optional  timer: Timer = timerManager.getPrimaryTimer()!!, time: Int) {
        timer.time = time
    }

    @CommandPermission("katzcrafttimer.display.timer")
    @Subcommand("displaymode timer")
    fun displaymode(actor: BukkitCommandActor, mode: TimerDisplayType) {
        if(actor.sender() is Player) {
            val player: Player = actor.sender() as Player
            playerSettingsManager.getSetting(player)!!.timerDisplayType = mode
        }
    }

    @CommandPermission("katzcrafttimer.display.paused")
    @Subcommand("displaymode paused")
    fun displaymode(actor: BukkitCommandActor, mode: PausedDisplayType) {
        if(actor.sender() is Player) {
            val player: Player = actor.sender() as Player
            playerSettingsManager.getSetting(player)!!.pauseDisplayType = mode
        }
    }
}