package cc.kinus.kitpvp.gui;

import cc.kinus.kitpvp.KitPVP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class GUIListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event){
        Inventory inventory = event.getInventory();
        if(!(event.getWhoClicked() instanceof Player)){
           return;
        }
        Player player = (Player) event.getWhoClicked();
        GUIManager guiManager = KitPVP.getInstance().getGuiManager();
        GUIBase guiBase = guiManager.getGuiByInventory(inventory);

        if(guiBase != null && player.equals(guiBase.getOwner())){
            event.setCancelled(true);
            guiBase.onClick(event.getRawSlot());// 处理点击背包操作
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        GUIManager guiManager = KitPVP.getInstance().getGuiManager();
        GUIBase guiBase = guiManager.getGuiByInventory(inventory);
        if(guiBase != null && player.equals(guiBase.getOwner())){
            guiManager.getOpenedGUIs().remove(guiBase);// 移除此GUI
        }
    }

    @EventHandler
    public void onQUit(PlayerQuitEvent event){
        Player player = (Player) event.getPlayer();
        GUIManager guiManager = KitPVP.getInstance().getGuiManager();
        GUIBase guiBase = guiManager.getPlayerGUI(player);
        if(guiBase != null){
            guiManager.getOpenedGUIs().remove(guiBase);
        }
    }
}
