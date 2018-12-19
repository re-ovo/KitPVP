package cc.kinus.kitpvp.gui.guis;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.gui.GUIBase;
import cc.kinus.kitpvp.kits.Kit;
import cc.kinus.kitpvp.kits.KitManager;
import cc.kinus.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KitGUI extends GUIBase {
    public KitGUI(Player owner) {
        super(owner, "&c选择你的职业", 27);
    }

    private Map<Integer,String> kitSlots = new HashMap<>();

    @Override
    public void onClick(int slot) {
        if(kitSlots.containsKey(slot)){
            // handle use the kit
            KitManager kitManager = KitPVP.getInstance().getKitManager();
            Kit kit = kitManager.getKitByName(kitSlots.getOrDefault(slot,""));
            if(kit == null){
                close();
                getOwner().sendMessage(ChatColor.RED + "错误: 未知的职业");
                return;
            }
            close();
            kitManager.selectKit(getOwner(),kit);
            getOwner().sendMessage(ChatColor.GREEN + "You have chosen a kit: "+ChatColor.RED+kit.getName());
        }
    }

    @Override
    public void update() {
        int slot = 0;
        for(Kit kit : KitPVP.getInstance().getKitManager().getKits()){
            //System.out.println("add kits in gui: "+kit.getName());// debug
            ItemStack itemStack =
                    ItemBuilder.build(kit.getMaterial(),"&r"+kit.getName(),kit.getDescription());
            getInventory().setItem(slot,itemStack);

            this.kitSlots.put(slot, kit.getName());
            slot++;
        }
    }
}
