package cc.kinus.kitpvp.inventory;

import cc.kinus.kitpvp.gui.guis.KitGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
    @EventHandler
    public void onUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            ItemStack itemStack = player.getItemInHand();
            if(itemStack == null || itemStack.getType() == Material.AIR){
                return;
            }
            if(!itemStack.hasItemMeta()){
                return;
            }
            if(!itemStack.getItemMeta().hasDisplayName()){
                return;
            }
            String name = itemStack.getItemMeta().getDisplayName();

            if(name.contains("Select Kits")){
                new KitGUI(player).open();
            }
        }
    }
}
