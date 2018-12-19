package cc.kinus.kitpvp.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemBuilder {
    public static ItemStack build(Material material,String name){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack build(Material material, String name, List<String> lore){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',name));
        itemMeta.setLore(lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&',s)).collect(Collectors.toList()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack build(Material material, Map<Enchantment,Integer> enchantmentIntegerMap){
        ItemStack itemStack = new ItemStack(material);
        itemStack.addEnchantments(enchantmentIntegerMap);
        return itemStack;
    }

    public static ItemStack build(Material material, Enchantment enchant,int level){
        ItemStack itemStack = new ItemStack(material);
        itemStack.addEnchantment(enchant,level);
        return itemStack;
    }

    public static ItemStack build(Material material, Enchantment enchant,int level,  Enchantment enchant2,int level2){
        ItemStack itemStack = new ItemStack(material);
        itemStack.addEnchantment(enchant,level);
        itemStack.addEnchantment(enchant2,level2);
        return itemStack;
    }

    public static ItemStack unbreak(ItemStack itemStack){
        ItemStack item = itemStack;
        ItemMeta meta = item.getItemMeta();
        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
    }

    public static String getName(ItemStack itemStack){
        if(!itemStack.hasItemMeta()){
            return itemStack.getType().name();
        }
        ItemMeta meta = itemStack.getItemMeta();
        if(!meta.hasDisplayName()){
            return itemStack.getType().name();
        }
        return meta.getDisplayName();
    }
}
