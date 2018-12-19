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

public class Archer extends Kit {
    public Archer() {
        super("Archer", Material.BOW);
        // 设置职业描述
        setDescription(Arrays.asList(
                "&f职业名字: &c弓箭手",
                "&f职业描述:",
                "  &7远程输出职业"
        ));
        // 设置职业盔甲
        setArmor(new ItemStack[]{
                ItemBuilder.build(Material.CHAINMAIL_BOOTS, Enchantment.PROTECTION_ENVIRONMENTAL,1),
                ItemBuilder.build(Material.CHAINMAIL_LEGGINGS,Enchantment.PROTECTION_ENVIRONMENTAL,1),
                ItemBuilder.build(Material.IRON_CHESTPLATE,Enchantment.PROTECTION_ENVIRONMENTAL,1),
                ItemBuilder.build(Material.CHAINMAIL_HELMET,Enchantment.PROTECTION_ENVIRONMENTAL,1,Enchantment.PROTECTION_PROJECTILE,3)
        });
        ItemStack bow = ItemBuilder.build(Material.BOW,Enchantment.ARROW_DAMAGE,2,Enchantment.ARROW_INFINITE,1);
        ItemBuilder.unbreak(bow);
        // 设置职业物品
        setItems(Arrays.asList(
                ItemBuilder.build(Material.IRON_SWORD,Enchantment.DAMAGE_ALL,1),
                bow,
                ItemBuilder.unbreak(new ItemStack(Material.FISHING_ROD)),
                new ItemStack(Material.GOLDEN_APPLE,3),
                new ItemStack(Material.ARROW)
        ));
    }

    @Override
    public void use(Player player) {
        new PotionEffect(PotionEffectType.SPEED,1000000,0).apply(player);
        new PotionEffect(PotionEffectType.JUMP,1000000,1).apply(player);
    }
}
