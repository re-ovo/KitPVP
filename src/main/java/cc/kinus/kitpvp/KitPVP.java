package cc.kinus.kitpvp;

import cc.kinus.kitpvp.commands.KitpvpCommand;
import cc.kinus.kitpvp.config.ConfigManager;
import cc.kinus.kitpvp.database.DatabaseManager;
import cc.kinus.kitpvp.game.ArenaManager;
import cc.kinus.kitpvp.gui.GUIListener;
import cc.kinus.kitpvp.gui.GUIManager;
import cc.kinus.kitpvp.inventory.InventoryListener;
import cc.kinus.kitpvp.kills.KillManager;
import cc.kinus.kitpvp.kits.KitManager;
import cc.kinus.kitpvp.listener.EntityListener;
import cc.kinus.kitpvp.listener.PlayerListener;
import cc.kinus.kitpvp.scoreboard.ScoreBoardManager;
import cc.kinus.kitpvp.stats.PlayerStatsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KitPVP extends JavaPlugin {
    private static KitPVP instance;

    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    private PlayerStatsManager playerStatsManager;
    private ArenaManager arenaManager;
    private GUIManager guiManager;
    private KitManager kitManager;
    private KillManager killManager;
    private ScoreBoardManager scoreboardManager;

    @Override
    public void onEnable() {
        instance = this;
        log("----------------------------------------------------------------");
        log("");

        // Load config manager
        log("加载配置文件...");
        this.configManager = new ConfigManager();

        // Load database manager (connection pool)
        log("加载数据库...");
        this.databaseManager = new DatabaseManager();

        // Load PlayerStats Manager
        this.playerStatsManager = new PlayerStatsManager();

        // Load Arena Manager
        this.arenaManager = new ArenaManager();

        // Load GUI Manager
        this.guiManager = new GUIManager();

        // Load KitManager
        this.kitManager = new KitManager();

        // Load strike kill manager
        this.killManager = new KillManager();

        this.scoreboardManager = new ScoreBoardManager();

        // 注册监
        // 听器
        this.registerListeners();

        Bukkit.getPluginCommand("kitpvp").setExecutor(new KitpvpCommand());

        log("KitPVP Load Done");
        log("");
        log("----------------------------------------------------------------");
    }

    /**
     * 注册监听器
     */
    public void registerListeners(){
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new GUIListener(),this);
        pluginManager.registerEvents(new PlayerListener(),this);
        pluginManager.registerEvents(new EntityListener(),this);
        pluginManager.registerEvents(new InventoryListener(),this);
    }

    @Override
    public void onDisable() {
        this.databaseManager.getDataSource().close(); // 关闭连接池
    }

    public void reload(){
        this.databaseManager.getDataSource().close();
        this.databaseManager = new DatabaseManager();

        this.configManager = new ConfigManager();

        this.scoreboardManager.reload();

        log("重载完成");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public KillManager getKillManager() {
        return killManager;
    }

    public PlayerStatsManager getPlayerStatsManager() {
        return playerStatsManager;
    }

    public ScoreBoardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }

    public static KitPVP getInstance() {
        return instance;
    }

    public static void log(String text){
        Bukkit.getConsoleSender().sendMessage(String.format("[%s] %s","KitPVP", ChatColor.translateAlternateColorCodes('&',text)));
    }
}
