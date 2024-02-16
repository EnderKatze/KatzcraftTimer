package de.enderkatze.katzcrafttimer.commands;

import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.Utils;
import de.enderkatze.katzcrafttimer.events.TimerPauseEvent;
import de.enderkatze.katzcrafttimer.events.TimerResumeEvent;
import de.enderkatze.katzcrafttimer.timer.Timer;
import de.enderkatze.katzcrafttimer.utitlity.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.*;

public class TimerCommand implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> subcommands = new HashMap<>();


    public void registerSubcommand(SubCommand subcommand) {
        subcommands.put(subcommand.getName(), subcommand);
    }

    String Prefix = Main.getInstance().getPrefix();

    String successColor = Main.getInstance().getConfig().getString("successColor");
    String errorColor = Main.getInstance().getConfig().getString("errorColor");


    String usageMessage = Main.getInstance().getLanguage().getString("usage");

    String alreadyRunningMessage = Main.getInstance().getLanguage().getString("alreadyRunning");
    String startedMessage = Main.getInstance().getLanguage().getString("started");

    String alreadyPausedMessage = Main.getInstance().getLanguage().getString("alreadyPaused");
    String pausedMessage = Main.getInstance().getLanguage().getString("paused");

    String resetMessage = Main.getInstance().getLanguage().getString("reset");

    String notNumberMessage = Main.getInstance().getLanguage().getString("timeNotValid")
            + " " + Main.getInstance().getLanguage().getString("notANumber");
    String noneMessage = Main.getInstance().getLanguage().getString("timeNotValid")
            + " " + Main.getInstance().getLanguage().getString("noValue");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {

            sender.sendMessage(Prefix + ChatColor.valueOf(errorColor) + usageMessage);
            return true;
        }
        String subcommandName = args[0].toLowerCase();
        boolean didExecuteCommand = handleSubcommand(subcommands.get(subcommandName), sender, args, 0, "katzcrafttimer");

        if(!didExecuteCommand) {
            sender.sendMessage(Prefix + Main.getInstance().getLanguage().getString("usage"));
        }


        return true;
    }

    private boolean handleSubcommand(SubCommand subCommand, CommandSender sender, String[] args, int depth, String topLevelPermission) {

        if(subCommand != null) {

            String subcommandPermission = topLevelPermission + "." + subCommand.getName();

            List<SubCommand> subSubCommands = subCommand.getSubcommands();
            if(subSubCommands != null) {

                if(args.length >=depth+1) {
                    for (SubCommand subSubCommand : subSubCommands) {
                        if (Objects.equals(subSubCommand.getName(), args[depth+1])) {

                            return handleSubcommand(subSubCommand, sender, args, depth + 1, subcommandPermission);

                        }
                    }
                    sender.sendMessage(Main.getInstance().getPrefix() + Main.getInstance().getLanguage().getString("invalidArgument"));
                } else {
                    if(sender.hasPermission(subcommandPermission)) {
                        subCommand.execute(sender, args);
                    }
                }
                return true;

            } else {
                if(sender.hasPermission(subcommandPermission)) {
                    subCommand.execute(sender, args);
                }
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if(args.length == 0) return list;

        if(args.length == 1) {

            list.add("resume");
            list.add("pause");
            list.add("reset");
            list.add("time");
            list.add("countdown");
            list.add("set_display");
            list.add("hologram");
            list.add("reload");

        } else if (args.length == 2) {

            if(Objects.equals(args[0], "time")) {
                list.add("set");
                list.add("add");
                list.add("remove");
            }
            else if(Objects.equals(args[0], "set_display")) {

                list.add("@a");

                for (Player player : Bukkit.getOnlinePlayers() ) {

                    list.add(player.getName());
                }
            }
        } else if (args.length == 3) {

            if(Objects.equals(args[0], "set_display")) {

                list.add("true");
                list.add("false");
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
