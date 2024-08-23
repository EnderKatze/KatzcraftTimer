package de.enderkatze.katzcrafttimer.commands;

import com.google.inject.Inject;
import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class TimerCommand implements CommandExecutor, TabCompleter {

    final Main plugin;
    final String prefix;

    final String successColor;
    final String errorColor;
    final String usageMessage;


    private final Map<String, SubCommand> subcommands = new HashMap<>();

    @Inject
    TimerCommand(Main plugin) {
        this.plugin = plugin;
        this.prefix = this.plugin.getPrefix();

        this.successColor = plugin.getConfig().getString("successColor");
        this.errorColor = plugin.getConfig().getString("errorColor");
        this.usageMessage = plugin.getLanguage().getString("usage");
    }

    public void registerSubcommand(SubCommand subcommand) {
        subcommands.put(subcommand.getName(), subcommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {

            sender.sendMessage(prefix + ChatColor.valueOf(errorColor) + usageMessage);
            return true;
        }
        String subcommandName = args[0].toLowerCase();
        boolean didExecuteCommand = handleSubcommand(subcommands.get(subcommandName), sender, args, 0, "katzcrafttimer");

        if(!didExecuteCommand) {
            sender.sendMessage(prefix + Main.getInstance().getLanguage().getString("usage"));
        }


        return true;
    }

    private boolean handleSubcommand(SubCommand subCommand, CommandSender sender, String[] args, int depth, String topLevelPermission) {

        if(subCommand != null) {

            String subcommandPermission = topLevelPermission + "." + subCommand.getName();


                if(sender.hasPermission(subcommandPermission)) {
                    subCommand.execute(sender, args);
                }
                return true;
            }
        return false;
    }


    // TODO fix github issue #1
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if(args.length == 0) return list;

        if(args.length == 1) {
            for (SubCommand subCommand : subcommands.values()) {
                list.add(subCommand.getName());
            }
        }

        if(args.length >= 2) {
            if(subcommands.containsKey(args[0])) {
                SubCommand selected = subcommands.get(args[0]);
                list = new ArrayList<String>(selected.getOptions());

            }
        }



        ArrayList<String> completerList = new ArrayList<>();
        String arg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if(s1.startsWith(arg)) {
                completerList.add(s);
            }
        }
        
        return completerList;
    }
}
