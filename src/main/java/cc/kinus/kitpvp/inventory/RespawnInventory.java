package cc.kinus.kitpvp.inventory;

import cc.kinus.kitpvp.utils.InventoryUtil;
import cc.kinus.kitpvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RespawnInventory {
    public static void set(Player player){
        InventoryUtil.reset(player);

        Inventory inventory = player.getInventory();
        inventory.setItem(0, ItemBuilder.build(Material.IRON_SWORD,"&cSelect Kits"));
    }
}
