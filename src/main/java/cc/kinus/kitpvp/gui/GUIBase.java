package cc.kinus.kitpvp.gui;

import cc.kinus.kitpvp.KitPVP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class GUIBase {
    private Player owner;
    private String title;
    private Inventory inventory;

    public GUIBase(Player owner, String title,int size) {
        this.owner = owner;
        this.title = title;
        this.inventory = Bukkit.createInventory(null,size, ChatColor.translateAlternateColorCodes('&',title));
        KitPVP.getInstance().getGuiManager().getOpenedGUIs().add(this);
    }

    public void open(){
        this.owner.openInventory(inventory);
        update();
    }

    public abstract void onClick(int slot);

    public abstract void update();

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void close(){
        owner.closeInventory();
        GUIManager guiManager = KitPVP.getInstance().getGuiManager();
        GUIBase guiBase = guiManager.getGuiByInventory(inventory);
        if(inventory != null){
            guiManager.getOpenedGUIs().remove(guiBase);// 移除此GUI
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GUIBase guiBase = (GUIBase) o;

        if (owner != null ? !owner.equals(guiBase.owner) : guiBase.owner != null) return false;
        if (title != null ? !title.equals(guiBase.title) : guiBase.title != null) return false;
        return inventory != null ? inventory.equals(guiBase.inventory) : guiBase.inventory == null;
    }

    @Override
    public int hashCode() {
        int result = owner != null ? owner.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (inventory != null ? inventory.hashCode() : 0);
        return result;
    }
}
