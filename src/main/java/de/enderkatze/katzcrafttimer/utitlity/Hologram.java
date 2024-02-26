package de.enderkatze.katzcrafttimer.utitlity;

import de.enderkatze.katzcrafttimer.Main;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hologram {
    @NonNull
    private final Location location;
    @NonNull
    private final String title;

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
    }

    private List<ArmorStand> lines = new ArrayList<>();

    private ArmorStand createLine(Location location) {
        ArmorStand line = location.getWorld().spawn(location, ArmorStand.class);
        line.setVisible(false);
        line.setCustomNameVisible(true);
        line.setMarker(true);
        return line;
    }
}
