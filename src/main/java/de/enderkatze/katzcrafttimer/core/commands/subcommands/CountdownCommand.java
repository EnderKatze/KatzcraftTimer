package de.enderkatze.katzcrafttimer.core.commands.subcommands;

import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import de.enderkatze.katzcrafttimer.core.timer.deprecated.TimerOld;
import de.enderkatze.katzcrafttimer.core.utitlity.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CountdownCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {

        String Prefix = KatzcraftTimer.getInstance().getPrefix();

        TimerOld timer = KatzcraftTimer.getInstance().getTimer();
        String errorColor = KatzcraftTimer.getInstance().getConfig().getString("errorColor");
        if(args.length != 2) {
            sender.sendMessage(Prefix + ChatColor.valueOf(errorColor) + KatzcraftTimer.getInstance().getLanguage().getString("noValue"));
            if(sender instanceof Player) {
                Player player = (Player) sender;
                player.playSound(player, Sound.valueOf(KatzcraftTimer.getInstance().getConfig().getString("negativeSound")), 100, 1);
            }
        } else {
            try {
                timer.setTime(Integer.parseInt(args[1]));
                timer.setBackwards(true);
                timer.setRunning(true);
                sender.sendMessage(Prefix + ChatColor.GREEN + KatzcraftTimer.getInstance().getLanguage().getString("countdownStarted"));
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    player.playSound(player, Sound.valueOf(KatzcraftTimer.getInstance().getConfig().getString("positiveSound")), 100, 0);
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(Prefix + ChatColor.valueOf(errorColor) + KatzcraftTimer.getInstance().getLanguage().getString("notANumber"));
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    player.playSound(player, Sound.valueOf(KatzcraftTimer.getInstance().getConfig().getString("negativeSound")), 100, 1);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "countdown";
    }

    @Override
    public List<String> getOptions() {
        return Arrays.asList("30", "60", "3600");
    }
}
