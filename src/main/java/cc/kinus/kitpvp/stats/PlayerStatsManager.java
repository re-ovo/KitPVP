package cc.kinus.kitpvp.stats;

import cc.kinus.kitpvp.KitPVP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStatsManager implements Listener {
    private Map<UUID, PlayerStats> statsMap;

    public PlayerStatsManager() {
        this.statsMap = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, KitPVP.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // 异步从数据库载入数据
        Bukkit.getScheduler().runTaskAsynchronously(KitPVP.getInstance(), () -> PlayerStatsManager.this.load(player));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        // 异步写入数据
        Bukkit.getScheduler().runTaskAsynchronously(KitPVP.getInstance(), () -> PlayerStatsManager.this.update(player));
    }

    public void asyncUpdate(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(KitPVP.getInstance(), () -> PlayerStatsManager.this.update(player));
    }

    public void load(Player player) {
        UUID uuid = player.getUniqueId();
        String name = player.getName();

        Connection connection = KitPVP.getInstance().getDatabaseManager().getConnection(); // 从连接池管理器取得数据库连接
        String SQL = "SELECT * FROM kitpvp_stats where uuid=?";
        PlayerStats stats = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, uuid.toString());
            // 查询
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int kills = resultSet.getInt("kills");
                int deaths = resultSet.getInt("deaths");
                double kd = resultSet.getDouble("kd");
                int score = resultSet.getInt("score");
                int star = resultSet.getInt("star");

                stats = new PlayerStats(name, uuid.toString(), kills, deaths, kd, score, star);
                this.statsMap.put(uuid, stats);
            } else {
                // Create data for the player
                stats = new PlayerStats(name, uuid.toString(), 0, 0, 0.0D, 0, 0);
                this.statsMap.put(uuid, stats);
                this.update(player);// 更新到数据库
            }
            // 关闭连接 回收数据库资源
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Player player) {
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        PlayerStats playerStats = this.getStats(player);
        if (playerStats == null) {
            return;
        }
        playerStats.update();
        Connection connection = KitPVP.getInstance().getDatabaseManager().getConnection(); // 从连接池管理器取得数据库连接
        String SQL = "INSERT INTO kitpvp_stats(uuid,name,kills,deaths,kd,score,star) VALUES(?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE uuid=VALUES(uuid),name=VALUES(name),kills=VALUES(kills),deaths=VALUES(deaths),kd=VALUES(kd),score=VALUES(score),star=VALUES(star)";
        try {
            PreparedStatement preparedStatementc = connection.prepareStatement(SQL);

            preparedStatementc.setString(1, uuid.toString());
            preparedStatementc.setString(2, name);
            preparedStatementc.setInt(3, playerStats.getKills());
            preparedStatementc.setInt(4, playerStats.getDeaths());
            preparedStatementc.setDouble(5, playerStats.getKd());
            preparedStatementc.setInt(6, playerStats.getScore());
            preparedStatementc.setInt(7, playerStats.getStar());

            preparedStatementc.executeUpdate();

            preparedStatementc.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerStats getStats(Player player) {
        return this.statsMap.getOrDefault(player.getUniqueId(), null);
    }
}
