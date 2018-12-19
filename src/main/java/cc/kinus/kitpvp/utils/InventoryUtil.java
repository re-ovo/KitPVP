package cc.kinus.kitpvp.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class InventoryUtil {
    /**
     * @Description
     * 重置玩家,会重置hp food inventory potions
     *
     * @param player 玩家对象
     */
    public static void reset(Player player){
        // 设置游戏模式
        player.setGameMode(GameMode.SURVIVAL);
        // 重置背包
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory();
        // 设置HP和饥饿值
        player.setHealth(20);
        player.setMaxHealth(20);
        player.setFoodLevel(20);
        // 去除药水效果
        player.getActivePotionEffects().stream().map(PotionEffect::getType).forEach(player::removePotionEffect);
        // 去除火焰
        player.setFireTicks(0);
    }
}
