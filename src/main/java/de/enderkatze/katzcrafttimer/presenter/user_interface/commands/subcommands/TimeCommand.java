package de.enderkatze.katzcrafttimer.presenter.user_interface.commands.subcommands;

import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import de.enderkatze.katzcrafttimer.infra.timer.deprecated.TimerOld;
import de.enderkatze.katzcrafttimer.domain.logic.utitlity.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;


public class TimeCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length >= 2) {
            String amount = args[1];

            TimerOld timer = KatzcraftTimer.getInstance().getTimer();

            String errorMessage = KatzcraftTimer.getInstance().getPrefix() + KatzcraftTimer.getInstance().getLanguage().getString("timeNotValid") + " " + KatzcraftTimer.getInstance().getLanguage().getString("notANumber");


            switch (amount.charAt(0)) {
                case '+':
                    try {
                        timer.setTime(timer.getTime() + Integer.parseInt(amount.substring(1)));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(errorMessage);
                    }
                    break;
                case '-':
                    try {
                        timer.setTime(timer.getTime() - Integer.parseInt(amount.substring(1)));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(errorMessage);
                    }
                    break;
                default:
                    try {
                        timer.setTime(Integer.parseInt(amount));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(errorMessage);
                    }
                    break;
            }
        }
    }

    @Override
    public String getName() {
        return "time";
    }

    @Override
    public List<String> getOptions() {
        return Arrays.asList("+30", "30", "-30");
    }
}
