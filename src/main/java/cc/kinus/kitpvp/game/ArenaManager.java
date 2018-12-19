package cc.kinus.kitpvp.game;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.config.configs.ArenaConfig;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * 竞技场管理器
 * 管理各个竞技场，加载保存竞技场
 */
public class ArenaManager {
    private List<Arena> arenas;

    public ArenaManager() {
        this.arenas = new ArrayList<>();
        load();
    }

    /**
     * 根据名字返回对应的竞技场
     * 找不到对应竞技场时返回null
     *
     * @param name 竞技场名字
     * @return 竞技场对象
     */
    public Arena getArenaByName(String name){
        for(Arena arena : this.arenas){
            if(arena.getName().equalsIgnoreCase(name)){
                return arena;
            }
        }
        return null;
    }

    /**
     * 获得玩家所在的Arena
     *
     * @param player 玩家对象
     * @return 竞技场对象
     */
    public Arena getPlayerArena(Player player){
        for(Arena arena : this.arenas){
            if(arena.getPlayers().contains(player)){
                return arena;
            }
        }
        return null;
    }

    /**
     * 从配置文件中加载所有竞技场
     * 通常在游戏开始时被调用
     */
    public void load(){
        this.arenas.clear();// 首先清空一遍竞技场

        ArenaConfig config = KitPVP.getInstance().getConfigManager().getArenaConfig();// 获得Arena配置文件
        YamlConfiguration configuration = config.getConfiguration();// 获得Yaml对象
        // 迭代key
        for(String name : configuration.getConfigurationSection("arenas").getKeys(false)){
            Location spawn = (Location) configuration.get("arenas." + name + ".spawn");
            Location loc1 = (Location) configuration.get("arenas." + name + ".loc1");
            Location loc2 = (Location) configuration.get("arenas." + name+".loc2");
            Location protect1 = (Location) configuration.get("arenas." + name + ".protect1");
            Location protect2 = (Location) configuration.get("arenas." + name + ".protect2");
            // 通过这些data实例化一个竞技场
            Arena arena = new Arena(name,spawn,loc1,loc2,protect1,protect2);
            // 添加到List
            this.arenas.add(arena);
            KitPVP.log("Loaded Arena: "+arena.getName());
        }
    }

    /**
     * 保存竞技场到配置文件
     */
    public void save(){
        ArenaConfig config = KitPVP.getInstance().getConfigManager().getArenaConfig();// 获得Arena配置文件
        YamlConfiguration configuration = config.getConfiguration();// 获得Yaml对象
        for(Arena arena : this.arenas){
            configuration.set("arenas."+arena.getName()+".spawn",arena.getSpawn());
            configuration.set("arenas."+arena.getName()+".loc1",arena.getLoc1());
            configuration.set("arenas."+arena.getName()+".loc2",arena.getLoc2());
            configuration.set("arenas."+arena.getName()+".protect1",arena.getProtect1());
            configuration.set("arenas."+arena.getName()+".protect2",arena.getProtect2());
        }
        config.save();// 保存
    }

    public List<Arena> getArenas() {
        return arenas;
    }
}
