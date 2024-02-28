package de.enderkatze.katzcrafttimer.commands.subcommands;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.Utils;
import de.enderkatze.katzcrafttimer.utitlity.Hologram;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;


public class HologramCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            String title;
            if(args.length > 1) {

                StringBuilder titleBuilder = new StringBuilder();


                for (int i = 1; i < args.length; i++) {

                    titleBuilder.append(" ").append(args[i]);
                }


                title = titleBuilder.toString();
            } else {
                title = Main.getInstance().getConfig().getString("defaultHologramTitle");
            }

            Hologram hologram = new Hologram(((Player) sender).getLocation(), title);
        }
    }

    @Override
    public String getName() {
        return "hologram";
    }

    @Override
    public List<String> getOptions() {
        return Arrays.asList("Title");
    }
}
