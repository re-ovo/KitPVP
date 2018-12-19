package cc.kinus.kitpvp.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LookUtil{
    static {
        // 初始化Material Set
        Set<Material> ms = new HashSet<>();
        ms.addAll(Arrays.asList(Material.values()));
        materials = ms;
    }
    private static Set<Material> materials;

    /**
     * 获取玩家对准的方块
     *
     * @param player 玩家对象
     * @return 对准的方块
     */
    public static Block getPlayerLookingBlock(Player player){
        List<Block> blocks = player.getLineOfSight(materials,7);
        for(Block block : blocks){
            if(block.getType() != Material.AIR){
                return block;
            }
        }
        return null;
    }
}
