package de.enderkatze.katzcrafttimer.presenter.user_interface.commands.subcommands;

import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import de.enderkatze.katzcrafttimer.api.events.TimerResumeEvent;
import de.enderkatze.katzcrafttimer.infra.timer.deprecated.TimerOld;
import de.enderkatze.katzcrafttimer.domain.logic.utitlity.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class ResumeCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {

        String Prefix = KatzcraftTimer.getInstance().getPrefix();

        FileConfiguration language = KatzcraftTimer.getInstance().getLanguage();

        TimerOld timer = KatzcraftTimer.getInstance().getTimer();
        if (timer.isRunning()) {
            sender.sendMessage(Prefix + ChatColor.valueOf(KatzcraftTimer.getInstance().getConfig().getString("errorColor")) + language.getString("alreadyRunning"));
            if(sender instanceof Player) {
                Player player = (Player) sender;
                player.playSound(player, Sound.valueOf(KatzcraftTimer.getInstance().getConfig().getString("negativeSound")), 100, 1);
            }
            return;
        }
        timer.setRunning(true);
        Bukkit.broadcastMessage(Prefix + ChatColor.valueOf(KatzcraftTimer.getInstance().getConfig().getString("successColor")) + language.getString("started"));
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player, Sound.valueOf(KatzcraftTimer.getInstance().getConfig().getString("positiveSound")), 100, 2);
        }
        TimerResumeEvent timerResumeEvent = new TimerResumeEvent(timer.getTime(), timer.isBackwards());
        KatzcraftTimer.getInstance().getServer().getPluginManager().callEvent(timerResumeEvent);
    }

    @Override
    public String getName() {
        return "resume";
    }

    @Override
    public List<String> getOptions() {
        return null;
    }
}
