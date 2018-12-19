package cc.kinus.kitpvp.config;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.config.configs.ArenaConfig;
import cc.kinus.kitpvp.config.configs.MainConfig;

public class ConfigManager {
    private MainConfig mainConfig = new MainConfig();
    private ArenaConfig arenaConfig = new ArenaConfig();

    public ConfigManager() {
        KitPVP instance = KitPVP.getInstance();
        if(!instance.getDataFolder().exists()){
            instance.getDataFolder().mkdirs();
        }
    }

    public void reload(){
        mainConfig.reload();
        arenaConfig.reload();
        KitPVP.log("Reloaded Config");
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public ArenaConfig getArenaConfig() {
        return arenaConfig;
    }
}
