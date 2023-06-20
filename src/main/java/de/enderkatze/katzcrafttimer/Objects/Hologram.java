package de.enderkatze.katzcrafttimer.Objects;

import org.bukkit.entity.ArmorStand;

import java.util.List;

public class Hologram {

    private final String name;
    private final int id;

    private List<ArmorStand> childHolograms;

    public Hologram(String name, int id) {

        this.name = name;
        this.id = id;
    }

    public void addChildHologram(ArmorStand hologram) {

        childHolograms.add(hologram);
    }

    public List<ArmorStand> getChildHolograms() {

        return childHolograms;
    }

    public String getName() {

        return name;
    }

    public int getId() {

        return id;
    }
}
