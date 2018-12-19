package cc.kinus.kitpvp.utils;

import org.bukkit.Location;

public class LocationUtil {
    /**
     * 判断某个位置是否在一个区域内，区域由 A B位置描述
     *
     * @param location 要判断的位置
     * @param a A位置
     * @param b B位置
     * @return 是否在该区域内
     */
    public static boolean isIn(Location location, Location a, Location b) {
        double ax = a.getX();
        double ay = a.getY();
        double az = a.getZ();

        double bx = b.getX();
        double by = b.getY();
        double bz = b.getZ();

        double minX = Math.min(ax,bx);
        double maxX = Math.max(ax,bx);

        double minY = Math.min(ay,by);
        double maxY = Math.max(ay,by);

        double minZ = Math.min(az,bz);
        double maxZ = Math.max(az,bz);

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }
}
