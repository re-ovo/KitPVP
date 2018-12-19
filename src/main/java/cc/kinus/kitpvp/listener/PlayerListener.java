package cc.kinus.kitpvp.listener;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.config.configs.ArenaConfig;
import cc.kinus.kitpvp.config.configs.MainConfig;
import cc.kinus.kitpvp.game.Arena;
import cc.kinus.kitpvp.game.ArenaManager;
import cc.kinus.kitpvp.inventory.RespawnInventory;
import cc.kinus.kitpvp.kills.KillManager;
import cc.kinus.kitpvp.kits.KitManager;
import cc.kinus.kitpvp.stats.PlayerStatsManager;
import cc.kinus.kitpvp.utils.InventoryUtil;
import cc.kinus.kitpvp.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerListener implements Listener {
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item.getType() == Material.GOLDEN_APPLE){
            new PotionEffect(PotionEffectType.REGENERATION,160,1).apply(player);
        }
    }

    @EventHandler
    public void onRightclick(PlayerInteractEvent event){
        if(event.getClickedBlock() != null){
            if(event.getClickedBlock().getType() == Material.CHEST || event.getClickedBlock().getType() == Material.ENDER_CHEST){
                if(!event.getPlayer().hasPermission("kitpvp.admin")){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        ArenaManager arenaManager = KitPVP.getInstance().getArenaManager();
        Arena arena = arenaManager.getPlayerArena(player);
        if(arena != null && !player.hasPermission("kitpvp.admin")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player){

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        // installing,don't work
        if(MainConfig.install){
            return;
        }

        event.setJoinMessage("");
        InventoryUtil.reset(player);

        ArenaManager arenaManager = KitPVP.getInstance().getArenaManager();
        if(arenaManager.getArenas().size() == 1) {
            arenaManager.getArenas().get(0).addPlayer(player);
        }else {
            player.teleport(ArenaConfig.spawn.clone());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        // 检查竞技场
        // 如果在某个竞技场
        // 就退出
        Player player = event.getPlayer();
        ArenaManager arenaManager = KitPVP.getInstance().getArenaManager();
        Arena arena = arenaManager.getPlayerArena(player);
        KitManager kitManager = KitPVP.getInstance().getKitManager();
        kitManager.removeKit(player,false);
        if(arena != null){
            arena.quit(player);
        }
        InventoryUtil.reset(player);
        event.setQuitMessage("");
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            ArenaManager arenaManager = KitPVP.getInstance().getArenaManager();
            Arena arena = arenaManager.getPlayerArena(player);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntityType() != EntityType.PLAYER){
            return;
        }
        Player player = (Player) event.getEntity();
        ArenaManager arenaManager = KitPVP.getInstance().getArenaManager();
        Arena arena = arenaManager.getPlayerArena(player);
        // IN MAIN LOBBY
        if(arena == null){
            event.setCancelled(true);
            return;
        }
        // IN GAME ARENA
        if(LocationUtil.isIn(player.getLocation(),arena.getProtect1(),arena.getProtect2())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event){
        if(event.getEntityType() != EntityType.PLAYER){
            return;
        }
        Player player = (Player) event.getEntity();
        ArenaManager arenaManager = KitPVP.getInstance().getArenaManager();
        Arena arena = arenaManager.getPlayerArena(player);
        // IN MAIN LOBBY
        if(arena == null){
            event.setCancelled(true);
            return;
        }
        // IN GAME ARENA
        if(LocationUtil.isIn(player.getLocation(),arena.getProtect1(),arena.getProtect2())){
            event.setCancelled(true);
        }else{
            if(event.getDamager() instanceof Player){
                Player damager = (Player) event.getDamager();
                KitPVP.getInstance().getKillManager().damage(player.getUniqueId(),damager.getUniqueId());
            }
            if(event.getDamager() instanceof Arrow){
                Arrow arrow = (Arrow) event.getDamager();
                if(arrow.getShooter() instanceof Player){
                    Player attacker = (Player) arrow.getShooter();
                    KitPVP.getInstance().getKillManager().damage(player.getUniqueId(),attacker.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent event){
        if(event.getEntityType() == EntityType.PLAYER && event.getCause() == EntityDamageEvent.DamageCause.FALL){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        ArenaManager arenaManager = KitPVP.getInstance().getArenaManager();
        KillManager killManager = KitPVP.getInstance().getKillManager();
        Arena arena = arenaManager.getPlayerArena(player);
        PlayerStatsManager playerStatsManager = KitPVP.getInstance().getPlayerStatsManager();

        event.setDeathMessage("");
        // IN MAIN LOBBY
        if(arena == null){
            event.setKeepInventory(true);
            InventoryUtil.reset(player);
            player.teleport(ArenaConfig.spawn);
            return;
        }
        // IN game
        event.setKeepInventory(true);
        KitManager kitManager = KitPVP.getInstance().getKitManager();
        kitManager.removeKit(player,false);
        RespawnInventory.set(player);
        player.teleport(arena.getSpawn());
        Player killer = killManager.getKiller(player);
        if(killer != null){
            killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));// give a golden apple
            killManager.addKills(killer);
            killer.setHealth(killer.getMaxHealth());

            playerStatsManager.getStats(killer).addKills();
            playerStatsManager.asyncUpdate(killer);

            int kills_killer = killManager.getStrikeKills(killer);
            int kills_deather = killManager.getStrikeKills(player);

            Bukkit.broadcastMessage(String.format("§c%s§7[§f%s§7] §ewas slain by §c%s§7[§f%s§7]",player.getName(),kills_deather,killer.getName(),kills_killer));
        }else{
            int kills_deather = killManager.getStrikeKills(player);
            Bukkit.broadcastMessage(String.format("§c%s§7[§f%s§7]§e died",player.getName(),kills_deather));
        }
        playerStatsManager.getStats(player).addDeath();// add deaths
        playerStatsManager.asyncUpdate(player);
        killManager.remove(player);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(player.hasPermission("kitpvp.admin")){
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(player.hasPermission("kitpvp.admin")){
            return;
        }
        event.setCancelled(true);
    }
}
