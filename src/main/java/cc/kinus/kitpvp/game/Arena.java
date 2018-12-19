package cc.kinus.kitpvp.game;

import cc.kinus.kitpvp.inventory.RespawnInventory;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    ///////////////////////////////////////////////////////////
    //            需要设置和读取的数据                      ////
    ///////////////////////////////////////////////////////////
    // 竞技场名字
    private String name;
    // 出生点
    private Location spawn;
    // 竞技场边界
    private Location loc1;
    private Location loc2;
    // 保护区域
    private Location protect1;
    private Location protect2;

    ////////////////////  无需读取的数据  /////////////////////
    // 此竞技场玩家
    private List<Player> players;

    public Arena(String name, Location spawn, Location loc1, Location loc2, Location protect1, Location protect2) {
        this.name = name;
        this.spawn = spawn;
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.protect1 = protect1;
        this.protect2 = protect2;
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player){
        this.players.add(player);
        player.teleport(this.spawn);
        RespawnInventory.set(player);
    }

    public void quit(Player player){
        this.players.remove(player);
    }

    public String getName() {
        return name;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getLoc1() {
        return loc1;
    }

    public Location getLoc2() {
        return loc2;
    }

    public Location getProtect1() {
        return protect1;
    }

    public Location getProtect2() {
        return protect2;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void setLoc1(Location loc1) {
        this.loc1 = loc1;
    }

    public void setLoc2(Location loc2) {
        this.loc2 = loc2;
    }

    public void setProtect1(Location protect1) {
        this.protect1 = protect1;
    }

    public void setProtect2(Location protect2) {
        this.protect2 = protect2;
    }
}
