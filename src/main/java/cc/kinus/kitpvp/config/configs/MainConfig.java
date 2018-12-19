package cc.kinus.kitpvp.config.configs;

import cc.kinus.kitpvp.config.ConfigBase;
import org.bukkit.Location;

public class MainConfig extends ConfigBase {
    public static boolean install;
    public MainConfig() {
        super("config.yml");
        // 缓存配置
        install = getConfiguration().getBoolean("install",false);
    }
}
