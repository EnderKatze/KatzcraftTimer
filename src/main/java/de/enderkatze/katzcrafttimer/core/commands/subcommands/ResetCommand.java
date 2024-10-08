package de.enderkatze.katzcrafttimer.core.commands.subcommands;

import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import de.enderkatze.katzcrafttimer.api.events.TimerPauseEvent;
import de.enderkatze.katzcrafttimer.core.timer.deprecated.TimerOld;
import de.enderkatze.katzcrafttimer.core.utitlity.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ResetCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        TimerOld timer = KatzcraftTimer.getInstance().getTimer();

        String Prefix = KatzcraftTimer.getInstance().getPrefix();

        timer.setRunning(false);
        timer.setBackwards(false);
        timer.setTime(0);
        Bukkit.broadcastMessage(Prefix + ChatColor.valueOf(KatzcraftTimer.getInstance().getConfig().getString("successColor")) + KatzcraftTimer.getInstance().getLanguage().getString("reset"));
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.playSound(player, Sound.valueOf(KatzcraftTimer.getInstance().getConfig().getString("positiveSound")), 100, 1);

        }

        TimerPauseEvent timerPauseEvent = new TimerPauseEvent(timer.getTime(), false);
        KatzcraftTimer.getInstance().getServer().getPluginManager().callEvent(timerPauseEvent);
    }

    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public List<String> getOptions() {
        return null;
    }
}
