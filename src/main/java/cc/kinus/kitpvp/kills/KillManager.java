package cc.kinus.kitpvp.kills;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KillManager {
    private Map<UUID,Integer> kills = new HashMap<>(); // 连杀数目
    private Map<UUID,UUID> lastDamager = new HashMap<>();// 上一个打的人

    public Integer getStrikeKills(Player player){
        return kills.getOrDefault(player.getUniqueId(),Integer.valueOf(0));
    }

    public Player getKiller(Player player){
        UUID killer = lastDamager.getOrDefault(player.getUniqueId(),null);
        if(killer == null){
            return null;
        }
        Player k = Bukkit.getPlayer(killer);
        return k;
    }

    public void damage(UUID uuid,UUID damager){
        this.lastDamager.put(uuid,damager);
    }

    public void addKills(Player player){
        int k = this.kills.getOrDefault(player.getUniqueId(),0);
        k ++;
        this.kills.put(player.getUniqueId(),k);
    }

    public void remove(Player player){
        this.kills.remove(player.getUniqueId());
        this.lastDamager.remove(player.getUniqueId());
    }
}
