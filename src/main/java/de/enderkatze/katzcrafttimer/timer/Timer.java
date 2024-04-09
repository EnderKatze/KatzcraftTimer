package de.enderkatze.katzcrafttimer.timer;


import de.enderkatze.katzcrafttimer.Main;
import de.enderkatze.katzcrafttimer.events.CountdownEndEvent;
import de.enderkatze.katzcrafttimer.utitlity.Hologram;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class Timer {

    @Getter
    @Setter
    private boolean running;
    @Getter
    @Setter
    private boolean backwards;
    @Getter
    private int time;
    private boolean displayActionbar;

    public int setTime(int time) {
        this.time = Math.abs(time);

        return this.time;
    }

    public Timer(boolean running, int time, boolean backwards, boolean displayActionbar) {
        this.running = running;
        this.time = time;
        this.backwards = backwards;

        this.displayActionbar = displayActionbar;

        run();
    }

    public void updateHolograms() {
        List<Hologram> hologramList = Main.getInstance().getHolograms();

        for(Hologram hologram : hologramList) {

            List<ArmorStand> lines = hologram.getLines();

            String daysString = String.valueOf((getTime()/60/60) / 24);
            lines.get(0).setCustomName(daysString + " Days");

            String hoursString = String.valueOf((getTime()/60/60) % 24);
            if((Integer.parseInt(hoursString) < 10)) {
                hoursString = "0" + String.valueOf(hoursString);
            }
            lines.get(1).setCustomName(hoursString + " Hours");

            String minutesString = String.valueOf((getTime() / 60) % 60);
            if((Integer.parseInt(minutesString) < 10)) {
                minutesString = "0" + String.valueOf(minutesString);
            }
            lines.get(2).setCustomName(minutesString + " Minutes");

            String secondsString = String.valueOf((getTime()%60));
            if((Integer.parseInt(secondsString) < 10)) {
                secondsString = "0" + String.valueOf(secondsString);
            }
            lines.get(3).setCustomName(secondsString + " Seconds");

        }
    }

    void UpdateScoreboard(Player player) {

        Scoreboard scoreboard = player.getScoreboard();

        for(Objective objective: scoreboard.getObjectives()) {

            for(String entry: objective.getScoreboard().getEntries()) {

                Score score = objective.getScore(entry);
            }
        }
    }

    public String getTimeString() {

        String timeString = "";

        int seconds = getTime() % 60;
        int minutes = (getTime() / 60) % 60;
        int hours = getTime() / 60 / 60;

        String secondsStr = String.valueOf(seconds);
        String minutesStr = String.valueOf(minutes);
        String hoursStr = String.valueOf(hours);

        if (seconds < 10) {
            secondsStr = "0" + String.valueOf(seconds);
        }
        if (minutes < 10) {
            minutesStr = "0" + String.valueOf(minutes);
        }
        if (hours < 10) {
            hoursStr = "0" + String.valueOf(hours);
        }

        timeString = hoursStr + ":" + minutesStr + ":" + secondsStr;

        return timeString;
    }

    public void sendActionBar() {

            for (Player player : Bukkit.getOnlinePlayers()) {

                UpdateScoreboard(player);

                if(!Main.getInstance().getToggledActionbarPlayers().contains(player)) {
                    if (!isRunning()) {

                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                                ChatColor.valueOf(Main.getInstance().getConfig().getString("timerColor")).toString() +
                                        ChatColor.BOLD +Main.getInstance().getLanguage().getString(
                                        "actionbarPausedMessage")));
                    } else {



                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                                ChatColor.valueOf(Main.getInstance().getConfig().getString("timerColor")).toString() +
                                        ChatColor.BOLD + getTimeString()));
                    }
                }
            }


    }

    private void run() {
        new BukkitRunnable(){
            @Override
            public void run() {
                sendActionBar();
                updateHolograms();
                if(!isRunning()) {
                    return;
                }

                if(isBackwards()) { setTime(getTime() - 1); }
                else { setTime(getTime() + 1); }

                if(isBackwards() && (getTime() <= 0)) {
                    CountdownEndEvent countdownEndEvent = new CountdownEndEvent();
                    Main.getInstance().getServer().getPluginManager().callEvent(countdownEndEvent);
                }

            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }
}
