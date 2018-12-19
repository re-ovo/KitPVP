package cc.kinus.kitpvp.gui;

import cc.kinus.kitpvp.KitPVP;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GUIManager extends BukkitRunnable {
    private List<GUIBase> openedGUIs = new ArrayList<>();

    public GUIManager() {
        this.runTaskTimer(KitPVP.getInstance(),20,20);
    }

    /**
     * 获取玩家此时的GUI
     *
     * @param player 玩家对象
     * @return GUI对象
     */
    public GUIBase getPlayerGUI(Player player){
        for(GUIBase guiBase : this.openedGUIs){
            if(guiBase.getOwner().equals(player)){
                return guiBase;
            }
        }
        return null;
    }

    /**
     * 根据Inventory对象获取玩家点击的GUI
     *
     * @param inventory Inventory对象
     * @return GUI对象
     */
    public GUIBase getGuiByInventory(Inventory inventory){
        for(GUIBase base : this.openedGUIs){
            if(base.getInventory().equals(inventory)){
                return base;
            }
        }
        return null;
    }

    public List<GUIBase> getOpenedGUIs() {
        return openedGUIs;
    }

    @Override
    public void run() {
        this.openedGUIs.forEach(GUIBase::update);
    }
}
