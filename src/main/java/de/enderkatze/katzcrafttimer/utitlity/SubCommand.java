package de.enderkatze.katzcrafttimer.utitlity;

import de.enderkatze.katzcrafttimer.commands.TimerCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface SubCommand {

    void execute(CommandSender sender, String[] args);

    String name = null;

    default String getName() {
        return name;
    }

    void execute();
}
