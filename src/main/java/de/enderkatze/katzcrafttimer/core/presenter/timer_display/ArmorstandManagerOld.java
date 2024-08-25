package de.enderkatze.katzcrafttimer.core.presenter.timer_display;

import de.enderkatze.katzcrafttimer.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;

@Deprecated
public class ArmorstandManagerOld {

    public ArmorStand createHologram(World world, Location location, String type) {

        ArmorStand stand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);

        stand.setGravity(false);
        stand.setVisible(false);
        stand.setCustomNameVisible(true);
        stand.setCustomName("Hologram Test");

        stand.getPersistentDataContainer().set(Main.getInstance().getHologramKey(), PersistentDataType.STRING, type);

        return stand;
    }
}
