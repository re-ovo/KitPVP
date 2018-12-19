package cc.kinus.kitpvp.scoreboard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Board {
    private Player owner;
    private String title;
    private List<String> lines;
    private Sideline sideline;

    public Board(Player owner, String title, List<String> lines) {

    }

    public synchronized void send(List<String> lines) {
        lines.stream().map(Board::color).forEach(this.sideline::add);
        this.sideline.flush();
    }

    public void send(String title, List<String> lines) {
        this.sideline.getSidebar().setName(color(title));
        send(lines);
    }

    public void remove() {
        this.sideline.getSidebar().remove();
    }

    /*
    彩色化字符串
     */
    public static String color(String source) {
        return ChatColor.translateAlternateColorCodes('&', source);
    }

    public static class Sidebar {

        private Player player;
        private HashMap<String, Integer> linesA;
        private HashMap<String, Integer> linesB;

        private Boolean a = true;

        private SpecificWriterHandler handler;

        private String getBuffer() {
            return a ? "A" : "B";
        }

        private HashMap<String, Integer> linesBuffer() {
            return a ? linesA : linesB;
        }

        private HashMap<String, Integer> linesDisplayed() {
            return (!a) ? linesA : linesB;
        }

        private void swapBuffer() {
            a = !a;
        }

        public Sidebar(Player p) {

        }

        public void send() {

        }

        public void remove() {

        }

        public void clear() {

        }

        public void setLine(String text, Integer line) {

        }

        public void removeLine(String text) {


        }

        public void setName(String displayName) {


        }

        public String getName() {
            return player.getName();
        }
    }

    public static class Sideline {
        Sidebar sb;

        HashMap<Integer, String> old = new HashMap<>();
        Deque<String> buffer = new ArrayDeque<>();

        public Sideline(Sidebar sb) {
            this.sb = sb;
        }

        public void clear() {

        }

        public void set(Integer i, String str) {

        }

        public String makeUnique(String str) {

            return str;
        }

        public void add(String s) {
            buffer.add(s);
        }

        public void flush() {

        }

        public void send() {
            sb.send();
        }

        public Integer getRemainingSize() {
            return 15 - buffer.size();
        }

        public Sidebar getSidebar() {
            return this.sb;
        }
    }

    public enum SpecificWriterType {
        DISPLAY, ACTIONCHANGE, ACTIONREMOVE;
    }

    public static class SpecificWriterHandler {
        private static String version;
        private static Class<?> healthclass;
        private static Object interger;

        static {
            version = getNMSVersion();
            try {
                healthclass = Class.forName(a("IScoreboardCriteria$EnumScoreboardHealthDisplay"));
                interger = healthclass.getEnumConstants()[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void write(PacketContainer container, SpecificWriterType type) {

        }


        public static String a(String str) {
            return "net.minecraft.server." + version + "." + str;
        }

        public static String b(String str) {
            return "org.bukkit.craftbukkit." + version + "." + str;
        }

        public static String getNMSVersion() {
            return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        }
    }

}
