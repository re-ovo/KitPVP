package cc.kinus.kitpvp.kits.kits;

import cc.kinus.kitpvp.kits.Kit;
import cc.kinus.kitpvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class Tank extends Kit {
    public Tank() {
        super("Tank", Material.DIAMOND_CHESTPLATE);
        // 设置职业描述
        setDescription(Arrays.asList(
                "&f职业名字: &c坦克",
                "&f职业描述:",
                "  &7配备厚重装甲",
                "  &7但是移动缓慢"
        ));
        // 设置职业盔甲
        setArmor(new ItemStack[]{
                ItemBuilder.build(Material.IRON_BOOTS, Enchantment.PROTECTION_ENVIRONMENTAL,2),
                ItemBuilder.build(Material.DIAMOND_LEGGINGS,Enchantment.PROTECTION_ENVIRONMENTAL,2,Enchantment.PROTECTION_PROJECTILE,1),
                ItemBuilder.build(Material.DIAMOND_CHESTPLATE,Enchantment.PROTECTION_ENVIRONMENTAL,2,Enchantment.PROTECTION_PROJECTILE,1),
                ItemBuilder.build(Material.IRON_HELMET,Enchantment.PROTECTION_ENVIRONMENTAL,2)
        });
        // 设置职业物品
        setItems(Arrays.asList(
                ItemBuilder.build(Material.IRON_SWORD,Enchantment.DAMAGE_ALL,1),
                ItemBuilder.unbreak(new ItemStack(Material.FISHING_ROD)),
                new ItemStack(Material.GOLDEN_APPLE,3)
        ));
    }

    @Override
    public void use(Player player) {
       new PotionEffect(PotionEffectType.SLOW,Integer.MAX_VALUE,0).apply(player);
    }
}
