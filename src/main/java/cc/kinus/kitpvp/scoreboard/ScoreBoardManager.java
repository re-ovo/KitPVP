package cc.kinus.kitpvp.scoreboard;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.commands.KitpvpCommand;
import cc.kinus.kitpvp.config.configs.MainConfig;
import cc.kinus.kitpvp.game.Arena;
import cc.kinus.kitpvp.kills.KillManager;
import cc.kinus.kitpvp.kits.Kit;
import cc.kinus.kitpvp.kits.KitManager;
import cc.kinus.kitpvp.stats.PlayerStats;
import cc.kinus.kitpvp.utils.LocationUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ScoreBoardManager extends BukkitRunnable implements Listener {
    private Map<UUID,OldBoard> boardMap = new ConcurrentHashMap<>();

    // data
    private String wait_title;
    private List<String> wait_lines;
    private String fight_title;
    private List<String> fight_lines;

    public ScoreBoardManager() {
        runTaskTimerAsynchronously(KitPVP.getInstance(),0,20);
        Bukkit.getPluginManager().registerEvents(this, KitPVP.getInstance());

        MainConfig mainConfig = KitPVP.getInstance().getConfigManager().getMainConfig();
        YamlConfiguration configuration = mainConfig.getConfiguration();

        wait_title = configuration.getString("scoreboard.wait.title");
        wait_lines = configuration.getStringList("scoreboard.wait.lines");
        fight_title = configuration.getString("scoreboard.fight.title");
        fight_lines = configuration.getStringList("scoreboard.fight.lines");
    }

    public void reload(){
        MainConfig mainConfig = KitPVP.getInstance().getConfigManager().getMainConfig();
        YamlConfiguration configuration = mainConfig.getConfiguration();

        wait_title = configuration.getString("scoreboard.wait.title");
        wait_lines = configuration.getStringList("scoreboard.wait.lines");
        fight_title = configuration.getString("scoreboard.fight.title");
        fight_lines = configuration.getStringList("scoreboard.fight.lines");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        OldBoard oldBoard = new OldBoard(player,"KitPVP", Arrays.asList(""));
        oldBoard.send(Arrays.asList("kinus.cc"));

        this.boardMap.put(player.getUniqueId(),oldBoard);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        this.boardMap.get(event.getPlayer().getUniqueId()).remove();
        this.boardMap.remove(event.getPlayer().getUniqueId());
    }

    public void update(){
        for(Map.Entry<UUID,OldBoard> oldBoardEntry : boardMap.entrySet()){
            UUID uuid = oldBoardEntry.getKey();
            OldBoard oldBoard = oldBoardEntry.getValue();
            Player player = Bukkit.getPlayer(uuid);
            if(player == null){
                continue;
            }
            Arena arena = KitPVP.getInstance().getArenaManager().getPlayerArena(player);
            if(arena == null){
                continue;
            }
            // Update Stars
            oldBoard.updateStars();

            // Wait
            if(LocationUtil.isIn(player.getLocation(),arena.getProtect1(),arena.getProtect2())){
                List<String> lines = trans(player,wait_lines);
                oldBoard.send(trans(player,wait_title),lines);
            }
            // Fight
            else{
                List<String> lines = trans(player,fight_lines);
                oldBoard.send(trans(player,fight_title),lines);
            }
        }
    }

    public String trans(Player player,String text){
        KitManager kitManager = KitPVP.getInstance().getKitManager();
        KillManager killManager = KitPVP.getInstance().getKillManager();
        PlayerStats playerStats = KitPVP.getInstance().getPlayerStatsManager().getStats(player);
        if(playerStats == null){
            return text;
        }
        Kit kit = kitManager.getPlayerKit(player);
        return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player,text))
                .replaceAll("%kit%",kit == null ? "Unknown" : kit.getName())
                .replaceAll("%kills%", String.valueOf(playerStats.getKills()))
                .replaceAll("%deaths%", String.valueOf(playerStats.getDeaths()))
                .replaceAll("%kd%", String.valueOf(playerStats.getKd()))
                .replaceAll("%streak%", String.valueOf(killManager.getStrikeKills(player)))
                .replaceAll("%star%", String.valueOf(playerStats.getStar()))
                .replaceAll("%score%",String.valueOf(playerStats.getScore()));
    }

    public List<String> trans(Player player,List<String> lines){
        return lines.stream().map(line -> trans(player,line)).collect(Collectors.toList());
    }

    @Override
    public void run() {
        update();
    }

    public Map<UUID, OldBoard> getBoardMap() {
        return boardMap;
    }
}
