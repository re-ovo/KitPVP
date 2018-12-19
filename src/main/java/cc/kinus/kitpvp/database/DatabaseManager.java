package cc.kinus.kitpvp.database;

import cc.kinus.kitpvp.KitPVP;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.YamlConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 基于HikariCP的数据库连接池管理类
 * <p>
 * 为什么选择HikariCP?
 * 1. 超高的性能
 * 2. 简介易用轻量级
 *
 * @author RE
 */
public class DatabaseManager {
    private HikariDataSource dataSource;

    public DatabaseManager() {
        HikariConfig config = new HikariConfig();
        YamlConfiguration configuration = KitPVP.getInstance().getConfigManager().getMainConfig().getConfiguration();

        // 设置数据库连接池属性
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        // 设置数据库连接配置
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(String.format("jdbc:mysql://%s/%s?autoReconnect=true", configuration.getString("database.url"), configuration.getString("database.name")));
        config.setUsername(configuration.getString("database.user"));
        config.setPassword(configuration.getString("database.password"));

        this.dataSource = new HikariDataSource(config);// 使用HikariConfig创建连接池实例
        this.createTables();
    }

    /**
     * 获取连接对象，如果获取失败，返回null
     *
     * @return 连接对象
     */
    public Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建数据表方法
     * 当连接池初始化完成时调用
     */
    public void createTables() {
        Connection connection = this.getConnection();
        if (connection == null) {
            KitPVP.log("获取连接失败！无法初始化数据表！");
            return;
        }
        String SQL = "CREATE TABLE IF NOT EXISTS kitpvp_stats(`uuid` varchar(255) NOT NULL, `name` varchar(255) NOT NULL, `kd` FLOAT(10,2) NOT NULL DEFAULT '0', `kills` int(11) NOT NULL DEFAULT '0', `deaths` int(11) NOT NULL DEFAULT '0', `score` int(11) NOT NULL DEFAULT '0', `star` int(11) NOT NULL DEFAULT '0', PRIMARY KEY (`uuid`))";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.executeUpdate();// 执行更新
            // 回收连接资源
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }
}
