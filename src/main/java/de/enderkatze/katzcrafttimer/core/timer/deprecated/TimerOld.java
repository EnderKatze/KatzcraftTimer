package de.enderkatze.katzcrafttimer.core.timer.deprecated;


import de.enderkatze.katzcrafttimer.KatzcraftTimer;
import de.enderkatze.katzcrafttimer.api.events.CountdownEndEvent;
import de.enderkatze.katzcrafttimer.core.utitlity.enums.pausedDisplaySettings;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class TimerOld {

    private boolean running;
    private boolean backwards;
    private int time;
    private boolean displayActionbar;

    BossBar bossBar = Bukkit.createBossBar("", BarColor.valueOf(KatzcraftTimer.getInstance().getConfig().getString("bossBarColor")), BarStyle.SOLID);

    private int seconds;
    private int minutes;
    private int hours;
    private boolean displayTimeOnPause;

    public boolean isRunning() {
        return running;
    }

    public boolean isBackwards() {
        return backwards;
    }

    public boolean isDisplayActionbar() {return this.displayActionbar;}

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setBackwards(boolean backwards) {

        this.backwards = backwards;
    }

    public void setDisplayActionbar(boolean display) {

        this.displayActionbar = display;
    }

    public int getTime() {
        return time;
    }

    public boolean getBackwards() {

        return backwards;
    }

    public int setTime(int time) {
        this.time = Math.abs(time);

        return this.time;
    }

    public TimerOld(boolean running, int time, boolean backwards, boolean displayActionbar, boolean displayTimeOnPause) {
        this.running = running;
        this.time = time;
        this.backwards = backwards;

        this.displayActionbar = displayActionbar;
        this.displayTimeOnPause = displayTimeOnPause;

        run();
    }

    public void updateHolograms() {

        List<ArmorStand> holograms = new ArrayList<>();

        for(World world : Bukkit.getWorlds()) {

            for(ArmorStand stand : world.getEntitiesByClass(ArmorStand.class)) {

                if(stand.getPersistentDataContainer().has(KatzcraftTimer.getInstance().getHologramKey(), PersistentDataType.STRING)) {

                    String colour = KatzcraftTimer.getInstance().getConfig().getString("hologramTimerColor");

                    switch (stand.getPersistentDataContainer().get(KatzcraftTimer.getInstance().getHologramKey(), PersistentDataType.STRING)) {

                        case "days":

                            String daysString = String.valueOf((getTime()/60/60) / 24);

                            stand.setCustomName(ChatColor.valueOf(colour) + daysString + " Days");
                            break;

                        case "hours":
                            String hoursString = String.valueOf((getTime()/60/60) % 24);

                            if((Integer.parseInt(hoursString) < 10)) {
                                hoursString = "0" + String.valueOf(hoursString);
                            }

                            stand.setCustomName(ChatColor.valueOf(colour) + hoursString + " Hours");
                            break;

                        case "minutes":
                            String minutesString = String.valueOf((getTime() / 60) % 60);

                            if((Integer.parseInt(minutesString) < 10)) {
                                minutesString = "0" + String.valueOf(minutesString);
                            }

                            stand.setCustomName(ChatColor.valueOf(colour) + minutesString + " Minutes");
                            break;

                        case "seconds":
                            String secondsString = String.valueOf((getTime()%60));

                            if((Integer.parseInt(secondsString) < 10)) {
                                secondsString = "0" + String.valueOf(secondsString);
                            }

                            stand.setCustomName(ChatColor.valueOf(colour) + secondsString + " Seconds");
                            break;

                        default:
                            break;
                    }
                }
            }
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

        seconds = getTime() % 60;
        minutes = (getTime() / 60) % 60;
        hours = getTime() / 60 / 60;

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

                if(!KatzcraftTimer.getInstance().getToggledActionbarPlayers().contains(player)) {
                    if (!isRunning()) {

                        pausedDisplaySettings setting;

                        try {
                            setting = pausedDisplaySettings.
                                    valueOf(KatzcraftTimer.getInstance().getConfig().getString("pauseDisplaySetting").toUpperCase());
                        } catch (IllegalArgumentException e) {
                            setting = pausedDisplaySettings.TIME;
                        }

                        switch (setting) {
                            case TIME:
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                                        ChatColor.valueOf(KatzcraftTimer.getInstance().getConfig().getString("timerColor")).toString() +
                                                ChatColor.ITALIC + ChatColor.BOLD + getTimeString()));
                                break;

                            case MESSAGE:
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                                        ChatColor.valueOf(KatzcraftTimer.getInstance().getConfig().getString("timerColor")).toString() +
                                                ChatColor.BOLD + KatzcraftTimer.getInstance().getLanguage().getString(
                                                "actionbarPausedMessage")));
                                break;
                            case HIDE:
                                break;
                        }
                    } else {

                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                                ChatColor.valueOf(KatzcraftTimer.getInstance().getConfig().getString("timerColor")).toString() +
                                        ChatColor.BOLD + getTimeString()));
                    }
                }
            }


    }

    public void sendBossBar(){


        for (Player player : Bukkit.getOnlinePlayers()) {

            UpdateScoreboard(player);

            if(!bossBar.getPlayers().contains(player)) {bossBar.addPlayer(player);}

            if(!KatzcraftTimer.getInstance().getToggledActionbarPlayers().contains(player)) {
                // paused
                if (!isRunning()) {


                    pausedDisplaySettings setting;

                    try {
                        setting = pausedDisplaySettings.
                                valueOf(KatzcraftTimer.getInstance().getConfig().getString("pauseDisplaySetting").toUpperCase());
                    } catch (IllegalArgumentException e) {
                        setting = pausedDisplaySettings.TIME;
                    }

                    switch (setting) {
                        case TIME:
                            bossBar.setTitle(ChatColor.valueOf(KatzcraftTimer.getInstance().getConfig().getString("timerColor")).toString() +
                                    ChatColor.ITALIC + ChatColor.BOLD + getTimeString());
                            break;

                        case MESSAGE:
                            bossBar.setTitle(ChatColor.valueOf(KatzcraftTimer.getInstance().getConfig().getString("timerColor")).toString() +
                                    ChatColor.BOLD + KatzcraftTimer.getInstance().getLanguage().getString(
                                    "actionbarPausedMessage"));
                            break;
                        case HIDE:
                            bossBar.removePlayer(player);
                            break;
                    }
                // running
                } else {

                    bossBar.setTitle(ChatColor.valueOf(KatzcraftTimer.getInstance().getConfig().getString("timerColor")).toString() +
                            ChatColor.BOLD + getTimeString());
                }

            } else {
                bossBar.removePlayer(player);
            }
        }
    }

    private void run() {
        new BukkitRunnable(){
            @Override
            public void run() {
                if (KatzcraftTimer.getInstance().getConfig().getBoolean("bossbarInsteadofActionbar")) {
                    sendBossBar();
                } else {
                    sendActionBar();
                }
                updateHolograms();
                if(!isRunning()) {
                    return;
                }

                if(isBackwards()) { setTime(getTime() - 1); }
                else { setTime(getTime() + 1); }

                if(getBackwards() && (getTime() <= 0)) {
                    CountdownEndEvent countdownEndEvent = new CountdownEndEvent();
                    KatzcraftTimer.getInstance().getServer().getPluginManager().callEvent(countdownEndEvent);
                }

            }
        }.runTaskTimer(KatzcraftTimer.getInstance(), 20, 20);
    }

    public void removeAllPlayersFromBossBar() {
        bossBar.removeAll();
    }
}
