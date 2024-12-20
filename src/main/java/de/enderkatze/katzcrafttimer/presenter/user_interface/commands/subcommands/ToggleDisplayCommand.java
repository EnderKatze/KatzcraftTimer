package de.enderkatze.katzcrafttimer.presenter.user_interface.commands.subcommands;

import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import de.enderkatze.katzcrafttimer.domain.logic.utitlity.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ToggleDisplayCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {


        List<Player> players = KatzcraftTimer.getInstance().getToggledActionbarPlayers();

        Player selected = args.length >= 2? Bukkit.getPlayer(args[1]) : (sender instanceof Player ? (Player) sender : null);

        if(selected != null) {
            if (players.contains(selected)) {
                players.remove(selected);
            } else {
                players.add(selected);
            }
        }
        else {

            sender.sendMessage(KatzcraftTimer.getInstance().getPrefix() + KatzcraftTimer.getInstance().getLanguage().getString("playerNotFound"));
        }

        KatzcraftTimer.getInstance().setToggledActionbarPlayers(players);

    }

    @Override
    public String getName() {
        return "toggle_display";
    }

    @Override
    public List<String> getOptions() {
        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            playerNames.add(player.getName());
        }

        return playerNames;
    }
}
