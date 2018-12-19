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

public class Shadow extends Kit {
    public Shadow() {
        super("Assassin", Material.FEATHER);
        // 设置职业描述
        setDescription(Arrays.asList(
                "&f职业名字: &c刺客",
                "&f职业描述:",
                "  &7像影子一样快速",
                "  &7刺杀敌人于无形"
        ));
        // 设置职业盔甲
        setArmor(new ItemStack[]{
                ItemBuilder.build(Material.GOLD_BOOTS, Enchantment.PROTECTION_ENVIRONMENTAL,2),
                ItemBuilder.build(Material.GOLD_LEGGINGS,Enchantment.PROTECTION_ENVIRONMENTAL,2),
                ItemBuilder.build(Material.GOLD_CHESTPLATE,Enchantment.PROTECTION_ENVIRONMENTAL,2),
                ItemBuilder.build(Material.GOLD_HELMET,Enchantment.PROTECTION_ENVIRONMENTAL,2)
        });
        // 设置职业物品
        setItems(Arrays.asList(
                ItemBuilder.build(Material.DIAMOND_SWORD,Enchantment.DAMAGE_ALL,2),
                ItemBuilder.unbreak(new ItemStack(Material.FISHING_ROD)),
                new ItemStack(Material.GOLDEN_APPLE,3)
        ));
    }

    @Override
    public void use(Player player) {
        new PotionEffect(PotionEffectType.SPEED,1000000,1).apply(player);
    }
}
