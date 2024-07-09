package de.enderkatze.katzcrafttimer.commands.subcommands;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToggleDisplayCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {


        List<Player> players = Main.getInstance().getToggledActionbarPlayers();

        Player selected = args.length >= 2? Bukkit.getPlayer(args[1]) : (sender instanceof Player ? (Player) sender : null);

        if(selected != null) {
            if (players.contains(selected)) {
                players.remove(selected);
            } else {
                players.add(selected);
            }
        }
        else {

            sender.sendMessage(Main.getInstance().getPrefix() + Main.getInstance().getLanguage().getString("playerNotFound"));
        }

        Main.getInstance().setToggledActionbarPlayers(players);

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
