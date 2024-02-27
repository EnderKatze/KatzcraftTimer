package de.enderkatze.katzcrafttimer.utitlity;

import de.enderkatze.katzcrafttimer.Main;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Hologram {
    @Getter private final Location location;

    @Getter private final ArmorStand title;

    // First line is the title
    @Getter private List<ArmorStand> lines = new ArrayList<>();

    public Hologram(@NotNull Location location, String title) {
        this.location = location;
        double lineSpacing = -.3;
        if(title == null) {
            this.title = createLine(this.location.add(0, lineSpacing, 0), true);
            this.title.setCustomName(Main.getInstance().getConfig().getString("defaultHologramTitle"));
        } else {
            this.title = createLine(this.location.add(0, lineSpacing, 0), true);
            this.title.setCustomName(title);
        }
        lines.add(this.title);

        for(int i = 0; i <= 3; i++) {
            ArmorStand line = this.location.getWorld().spawn(this.location.add(0, lineSpacing * (i+2), 0), ArmorStand.class);
            lines.add(line);
        }

        Main.getInstance().addHologram(this);
        saveToFile();
    }

    public Hologram(@NotNull String id) {

        this.title  = getTitleById(id);

        if(this.title != null) {
            this.location = title.getLocation().add(0, .3, 0);

            FileConfiguration data = Main.getInstance().getDataConfig();
            ConfigurationSection hologramSection = data.getConfigurationSection("hologram." + id + ".lines");

            for (String key : hologramSection.getKeys(false)) {
                String lineId = hologramSection.getString(key);

                ArmorStand line = (ArmorStand) Bukkit.getEntity(UUID.fromString(lineId));

                if (line != null) {
                    this.lines.add(line);
                }
            }

        } else {
            this.location = null;
        }

    }

    private ArmorStand createLine(Location location, Boolean hasHitbox) {
        ArmorStand line = location.getWorld().spawn(location, ArmorStand.class);
        line.setVisible(false);
        line.setCustomNameVisible(true);
        line.setMarker(!hasHitbox);
        line.setInvulnerable(true);
        return line;
    }

    private void saveToFile() {


        if(this.title != null && this.lines.size() > 1) {

            FileConfiguration data = Main.getInstance().getDataConfig();

            data.set("hologram." + this.title.getUniqueId() + ".name", this.title.getCustomName());

            for (int i = 1; i < this.lines.size() - 1; i++) {

                ArmorStand line = this.lines.get(i);
                data.set("holograms." + this.title.getUniqueId() + ".lines." + i, line.getEntityId());


            }
            Main.getInstance().saveData();
        }
    }

    public void removeHologram() {
        if(this.title != null && !this.lines.isEmpty()) {
            this.title.remove();

            for(ArmorStand line : this.lines) {
                line.remove();
            }

            FileConfiguration data = Main.getInstance().getDataConfig();

            ConfigurationSection hologramsSection = data.getConfigurationSection("holograms");


        }
    }

    private ArmorStand getTitleById(String id) {

        FileConfiguration data = Main.getInstance().getDataConfig();
        ConfigurationSection hologramsSection = data.getConfigurationSection("holograms");

        if(hologramsSection.contains(id)) {

            return (ArmorStand) Bukkit.getEntity(UUID.fromString(id));
        }
        return null;
    }
}
