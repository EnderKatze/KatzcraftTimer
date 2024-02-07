package de.enderkatze.katzcrafttimer.utitlity;

import de.enderkatze.katzcrafttimer.commands.TimerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {

    void execute(CommandSender sender, String[] args);

    String getName();

    List<SubCommand> getSubcommands();
}
