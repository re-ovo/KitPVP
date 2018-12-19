package cc.kinus.kitpvp.config.configs;

import cc.kinus.kitpvp.config.ConfigBase;
import org.bukkit.Location;

public class ArenaConfig extends ConfigBase {
    public static Location spawn;

    public ArenaConfig() {
        super("arena.yml");

        spawn = (Location) getConfiguration().get("spawn");
    }
}
