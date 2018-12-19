package cc.kinus.kitpvp.kits.kits;

import cc.kinus.kitpvp.kits.Kit;
import cc.kinus.kitpvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Warrior extends Kit {
    public Warrior() {
        super("Warrior", Material.IRON_SWORD);
        // 设置职业描述
        setDescription(Arrays.asList(
                "&f职业名字: &c战士",
                "&f职业描述:",
                "  &7各方面非常均衡的职业"
        ));
        // 设置职业盔甲
        setArmor(new ItemStack[]{
                ItemBuilder.build(Material.IRON_BOOTS, Enchantment.PROTECTION_ENVIRONMENTAL,2),
                ItemBuilder.build(Material.IRON_LEGGINGS,Enchantment.PROTECTION_ENVIRONMENTAL,2),
                ItemBuilder.build(Material.IRON_CHESTPLATE,Enchantment.PROTECTION_ENVIRONMENTAL,2),
                ItemBuilder.build(Material.IRON_HELMET,Enchantment.PROTECTION_ENVIRONMENTAL,2,Enchantment.PROTECTION_PROJECTILE,1)
        });
        // 设置职业物品
        setItems(Arrays.asList(
                ItemBuilder.build(Material.DIAMOND_SWORD,Enchantment.DAMAGE_ALL,1),
                ItemBuilder.unbreak(new ItemStack(Material.FISHING_ROD)),
                new ItemStack(Material.GOLDEN_APPLE,3)
        ));
    }

    @Override
    public void use(Player player) {

    }
}
