package de.enderkatze.katzcrafttimer.utitlity;

import de.enderkatze.katzcrafttimer.Main;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Hologram {
    @Getter private final Location location;

    @Getter private final ArmorStand title;

    /**
     * -- GETTER --
     * Returns a list of ArmorStands, each representing a line in the hologram. These are sorted top to bottom line
     */
    @Getter private List<ArmorStand> lines = new ArrayList<>();


    public Hologram(@NotNull Location location, String title) {
        this.location = location;
        double lineSpacing = -.3;
        if(title == null) {
            this.title = createLine(this.location.clone().add(0, lineSpacing, 0), true);
            this.title.setCustomName(Main.getInstance().getConfig().getString("defaultHologramTitle"));
            this.title.getPersistentDataContainer().set(Main.getInstance().getHologramKey(), PersistentDataType.STRING, HologramLineType.TITLE.toString());
        } else {
            this.title = createLine(this.location.clone().add(0, lineSpacing, 0), true);
            this.title.setCustomName(title);
        }

        for(int i = 0; i < 4; i++) {
            ArmorStand line = createLine(location.clone().add(0, (i+2) * lineSpacing, 0), false);
            line.getPersistentDataContainer().set(Main.getInstance().getHologramKey(), PersistentDataType.STRING, HologramLineType.LINE.toString());
            lines.add(line);
        }

        Main.getInstance().addHologram(this);
        saveToFile();
    }

    public Hologram(@NotNull String id) {

        this.title  = getTitleById(id);

        if(this.title != null) {
            this.location = title.getLocation().clone().add(0, .3, 0);

            FileConfiguration data = Main.getInstance().getDataConfig();
            ConfigurationSection hologramSection = data.getConfigurationSection("hologram." + id + ".lines");

            if(hologramSection != null) {
                for (String key : hologramSection.getKeys(false)) {
                    String lineId = hologramSection.getString(key);

                    ArmorStand line = (ArmorStand) Bukkit.getEntity(UUID.fromString(lineId));

                    if (line != null) {
                        this.lines.add(line);
                    }
                }
            }

        } else {
            this.location = null;
        }

    }

    private ArmorStand createLine(@NotNull Location location, @NotNull Boolean hasHitbox) {
        ArmorStand line = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        line.setVisible(false);
        line.setCustomNameVisible(true);
        line.setMarker(!hasHitbox);
        line.setInvulnerable(true);
        line.setGravity(false);
        return line;
    }

    private void saveToFile() {


        if(this.title != null && this.lines.size() > 1) {

            FileConfiguration data = Main.getInstance().getDataConfig();

            data.set("hologram." + this.title.getUniqueId() + ".name", this.title.getCustomName());

            for (int i = 1; i < this.lines.size() - 1; i++) {

                ArmorStand line = this.lines.get(i);
                data.set("holograms." + this.title.getUniqueId() + ".lines." + i, line.getUniqueId());


            }
            Main.getInstance().saveData();
        }
    }

    public void removeHologram() {
        if(this.title != null && !this.lines.isEmpty()) {

            Main.getInstance().removeHologram(this);

            FileConfiguration data = Main.getInstance().getDataConfig();
            data.set("hologram." + this.title.getUniqueId(), null);

            this.title.remove();

            for(ArmorStand line : this.lines) {
                line.remove();
            }

            this.lines.clear();
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

