package cc.kinus.kitpvp.kits;

import cc.kinus.kitpvp.kits.kits.Archer;
import cc.kinus.kitpvp.kits.kits.Shadow;
import cc.kinus.kitpvp.kits.kits.Tank;
import cc.kinus.kitpvp.kits.kits.Warrior;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * KitManager
 *
 * 用于管理玩家所用的职业
 *
 * @Author RE
 */
public class KitManager {
    private List<Kit> kits;

    /**
     * 构造方法
     *
     * 在构造方法里初始化各个职业
     * 将其放入kits List，方便在之后进行遍历操作
     */
    public KitManager() {
        this.kits = new ArrayList<>();
        // Add all kits
        this.kits.add(new Warrior());
        this.kits.add(new Shadow());
        this.kits.add(new Tank());
        this.kits.add(new Archer());
    }

    /**
     * 通过名字获取职业
     *
     * @param name 职业名字
     * @return 职业对象
     */
    public Kit getKitByName(String name){
        for(Kit kit : this.kits){
            if(kit.getName().equalsIgnoreCase(name)){
                return kit;
            }
        }
        return null;
    }

    /**
     * 获取玩家正在使用的职业
     * 无职业返回null
     *
     * @param player 要获取的玩家
     * @return 职业
     */
    public Kit getPlayerKit(Player player){
        UUID uuid = player.getUniqueId();
        for(Kit kit : this.kits){
            if(kit.usingPlayers.contains(uuid)){
                return kit;
            }
        }
        return null;
    }

    /**
     * 设置玩家选择的职业
     * 但是并不会给与玩家任何职业物品
     * >> 通常情况下不会调用
     *
     * @param player 玩家对象
     * @param kit 职业对象
     */
    public void setPlayerKit(Player player,Kit kit){
        // 判断using player里是否已经有这个玩家了，如果有删除，并且使用递归算法再次调用本方法，以防止有多个重复玩家uuid对象
        if(kit.usingPlayers.contains(player.getUniqueId())){
            kit.usingPlayers.remove(player.getUniqueId());
            setPlayerKit(player,kit);
            return;
        }
        kit.usingPlayers.add(player.getUniqueId());
    }

    /**
     * 选择职业方法
     * 通常在点击选择的职业按钮后被调用
     * 它会给与玩家物品，并且调用setPlayerKit方法设置玩家正在使用的职业
     *
     * @param player 玩家对象
     * @param kit 职业对象
     */
    public void selectKit(Player player, Kit kit){
        // 设置下正在使用这个职业
        this.setPlayerKit(player,kit);
        // 清空背包
        player.getInventory().clear();
        // 给与物品
        PlayerInventory inventory = player.getInventory();
        kit.getItems().stream().forEach(inventory::addItem);
        // 设置盔甲
        inventory.setArmorContents(kit.getArmor());
        // 更新背包
        player.updateInventory();
        // 执行use
        kit.use(player);
    }

    /**
     * 清空玩家所使用的职业
     * 通常在玩家死后或者退出时被调用
     *
     * @param player
     */
    public void removeKit(Player player,boolean clearInventory){
        Kit kit = this.getPlayerKit(player);
        if(kit != null){
            kit.usingPlayers.remove(player.getUniqueId());
            if(clearInventory){
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                player.updateInventory();
            }
        }
    }

    /**
     * 返回所有职业
     *
     * @return 职业列表
     */
    public List<Kit> getKits() {
        return kits;
    }
}
