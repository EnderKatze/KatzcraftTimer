package de.enderkatze.katzcrafttimer.core.utitlity;

import org.bukkit.command.CommandSender;


import java.util.List;

public interface SubCommand {

    void execute(CommandSender sender, String[] args);

    String getName();

    List<String> getOptions();
}
