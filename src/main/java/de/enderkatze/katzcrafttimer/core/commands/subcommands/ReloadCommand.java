package de.enderkatze.katzcrafttimer.core.commands.subcommands;

import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import de.enderkatze.katzcrafttimer.core.utitlity.SubCommand;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        KatzcraftTimer.getInstance().reloadConfig();

        sender.sendMessage(KatzcraftTimer.getInstance().getPrefix() + KatzcraftTimer.getInstance().getLanguage().getString("reload"));
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.playSound(player, Sound.valueOf(KatzcraftTimer.getInstance().getConfig().getString("positiveSound")), 100, 0);
        }
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public List<String> getOptions() {
        return null;
    }
}
