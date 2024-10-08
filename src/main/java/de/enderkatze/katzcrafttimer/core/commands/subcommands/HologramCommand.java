package de.enderkatze.katzcrafttimer.core.commands.subcommands;

import de.enderkatze.katzcrafttimer.core.presenter.timer_display.ArmorstandManagerOld;
import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import de.enderkatze.katzcrafttimer.core.utitlity.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;


// TODO rework how holograms are stored
public class HologramCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            ArmorstandManagerOld armorstandManagerOld = new ArmorstandManagerOld();


            ArmorStand titleHologram = armorstandManagerOld.createHologram(player.getWorld(), player.getLocation().add(0, -.3, 0), "none");

            if(args.length > 1) {

                StringBuilder title = new StringBuilder();


                for (int i = 1; i < args.length; i++) {

                    title.append(" ").append(args[i]);
                }


                titleHologram.setCustomName(title.toString().replace('&', '§'));
            } else {
                titleHologram.setCustomName(KatzcraftTimer.getInstance().getConfig().getString("defaultHologramTitle"));
            }

            armorstandManagerOld.createHologram(player.getWorld(), player.getLocation().add(0, -.6, 0), "days");
            armorstandManagerOld.createHologram(player.getWorld(), player.getLocation().add(0, -.9, 0), "hours");
            armorstandManagerOld.createHologram(player.getWorld(), player.getLocation().add(0, -1.2, 0), "minutes");
            armorstandManagerOld.createHologram(player.getWorld(), player.getLocation().add(0, -1.5, 0), "seconds");
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
