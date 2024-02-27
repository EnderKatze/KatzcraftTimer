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

public class Hologram {
    @Getter private final Location location;

    @Getter private final String title;

    public Hologram(@NotNull Location location, String title) {
        this.location = location;
        if(title == null) {
            this.title = Objects.requireNonNull(Main.getInstance().getConfig().getString("defaultHologramTitle"));
        } else {
            this.title = title;
        }

        ArmorStand titleLine = createLine(this.location.add(0, 1.5, 0));
        titleLine.setCustomName(this.title);
        lines.add(titleLine);

        saveToFile();
    }

    public Hologram(@NotNull int id) {

        ArmorStand titleLine = getTitleById(id);

        if(titleLine != null) {
            this.title = titleLine.getCustomName();
            this.location = titleLine.getLocation().add(0, -1.5, 0);
        } else {
            this.title = null;
            this.location = null;
        }
    }


    private List<ArmorStand> lines = new ArrayList<>();

    private ArmorStand createLine(Location location) {
        ArmorStand line = location.getWorld().spawn(location, ArmorStand.class);
        line.setVisible(false);
        line.setCustomNameVisible(true);
        line.setMarker(true);
        return line;
    }

    private void saveToFile() {

        ArmorStand title = this.lines.get(0);

        FileConfiguration data = Main.getInstance().getDataConfig();

        data.set("hologram." + title.getEntityId() + ".name", title.getCustomName());

        for(int i = 1; i < this.lines.size()-1; i++) {

            ArmorStand line = this.lines.get(i);
            data.set("holograms." + title.getEntityId() + ".lines." + i, line.getEntityId());


        }
        Main.getInstance().saveData();
    }

    public void removeHologram() {

    }

    public ArmorStand getTitleById(int id) {

        FileConfiguration data = Main.getInstance().getDataConfig();
        ConfigurationSection hologramsSection = data.getConfigurationSection("holograms");

        for (Entity entity : Bukkit.getWorlds().get(0).getLivingEntities()) {
            if (entity instanceof ArmorStand && entity.getEntityId() == id) {
                return (ArmorStand) entity;
            }
        }
        
        return null;
    }
}
