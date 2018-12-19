package cc.kinus.kitpvp.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 职业类
 * 所有职业需要继承此类
 *
 * @Author RE
 */
public abstract class Kit {
    private String name; // 职业名字
    private Material material; // icon type
    private List<String> description; // 职业描述，显示于Lore
    private List<ItemStack> items; // 职业背包物品
    private ItemStack[] armor; // 职业装甲

    protected List<UUID> usingPlayers = new ArrayList<>();

    public Kit(String name,Material material) {
        this.name = name;
        this.material = material;
    }

    // 玩家使用职业之后调用
    // 通常用来给与药水效果
    public abstract void use(Player player);

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public List<UUID> getUsingPlayers() {
        return usingPlayers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kit kit = (Kit) o;

        if (name != null ? !name.equals(kit.name) : kit.name != null) return false;
        if (material != kit.material) return false;
        if (description != null ? !description.equals(kit.description) : kit.description != null) return false;
        if (items != null ? !items.equals(kit.items) : kit.items != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(armor, kit.armor)) return false;
        return usingPlayers != null ? usingPlayers.equals(kit.usingPlayers) : kit.usingPlayers == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (material != null ? material.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(armor);
        result = 31 * result + (usingPlayers != null ? usingPlayers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Kit{" +
                "name='" + name + '\'' +
                ", material=" + material +
                ", description=" + description +
                ", items=" + items +
                ", armor=" + Arrays.toString(armor) +
                ", usingPlayers=" + usingPlayers +
                '}';
    }
}
