package cc.kinus.kitpvp.scoreboard;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.game.Arena;
import cc.kinus.kitpvp.stats.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class OldBoard extends Board{

    private static final List<String> uniqueList = Arrays.asList(
      "§1§r", "§2§r", "§3§r", "§4§r", "§5§r", "§6§r", "§7§r", "§8§r",
            "§a§r","§b§r", "§c§r", "§d§r","§e§r","§f§r","§9§r"
    );

    private Scoreboard scoreboard;
    private Objective objective;
    private Player player;
    private List<Team> teams = new ArrayList<>();
    private String title = "";

    private Objective star;
    private Objective hp;


    public OldBoard(Player owner, String title, List<String> lines) {
        super(owner,title,lines);
        this.player = owner;
        this.title = title;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        owner.setScoreboard(scoreboard);

        objective = scoreboard.registerNewObjective("LiteBoard","dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(color(title));// 设置标题

        this.enableHP();
    }

    public void enableHP(){
        star = scoreboard.registerNewObjective("KIT_PVP_STAR","dummy");
        star.setDisplayName("§6✯");
        star.setDisplaySlot(DisplaySlot.BELOW_NAME);

        hp = scoreboard.registerNewObjective("KIT_PVP_HP","dummy");
        hp.setDisplaySlot(DisplaySlot.PLAYER_LIST);
    }

    public void updateStars(){
        for(Map.Entry<UUID,OldBoard> oldBoardEntry : KitPVP.getInstance().getScoreboardManager().getBoardMap().entrySet()){
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
            PlayerStats playerStats = KitPVP.getInstance().getPlayerStatsManager().getStats(player);
            int star = playerStats.getStar();

            this.star.getScore(player.getName()).setScore(star);
            this.hp.getScore(player.getName()).setScore((int) player.getHealth());
        }
    }

    @Override
    public void remove() {
        this.player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        this.objective.unregister();
        this.scoreboard = null;
    }

    @Override
    public synchronized void send(List<String> lines) {
        if(teams.size() == lines.size()){
            // 如果Teams数量和行数量相等，无需更新team,直接设置prefix + suffix
            for(int i = 0;i < lines.size();i++){
                Team t = teams.get(i);
                String[] sp = splitString(lines.get(i));
                t.setPrefix(sp[0]);
                t.setSuffix(sp[1]);
            }
        }else{
            // 如果不相等，需要重新创建teams

            // 1. 注销旧team和板子
            for(Team team : teams){
                team.unregister();
            }
            teams.clear();
            objective.unregister();
            // 注册新obj
            objective = scoreboard.registerNewObjective("LiteBoard","dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(color(this.title));// 设置标题

            // 2.注册新team
            for(int i = 0;i < lines.size();i++){
                String text = lines.get(i);
                Team t = scoreboard.registerNewTeam("LB-Line:"+(i + 1));
                t.addEntry(uniqueList.get(i));
                String[] sp = splitString(text);
                t.setPrefix(sp[0]);
                t.setSuffix(sp[1]);
                teams.add(t);

                objective.getScore(uniqueList.get(i)).setScore(lines.size() - i);
            }
        }
    }

    @Override
    public void send(String title, List<String> lines) {
        if(this.objective != null){
            this.objective.setDisplayName(color(title));
            this.send(lines);
        }
    }

    private String getResult(boolean BOLD, boolean ITALIC, boolean MAGIC, boolean STRIKETHROUGH, boolean UNDERLINE, ChatColor color) {
        return ((color != null) && (!color.equals(ChatColor.WHITE)) ? color : "") + "" + (BOLD ? ChatColor.BOLD : "") + (ITALIC ? ChatColor.ITALIC : "") + (MAGIC ? ChatColor.MAGIC : "") + (STRIKETHROUGH ? ChatColor.STRIKETHROUGH : "") + (UNDERLINE ? ChatColor.UNDERLINE : "");
    }

    private String[] splitString(String string) {
        StringBuilder prefix = new StringBuilder(string.substring(0, string.length() >= getMaxSize() ? getMaxSize() : string.length()));
        StringBuilder suffix = new StringBuilder(string.length() > getMaxSize() ? string.substring(getMaxSize()) : "");
        if ((prefix.toString().length() > 1) && (prefix.charAt(prefix.length() - 1) == '§')) {
            prefix.deleteCharAt(prefix.length() - 1);
            suffix.insert(0, '§');
        }
        int length = prefix.length();
        boolean PASSED;
        boolean UNDERLINE;
        boolean STRIKETHROUGH;
        boolean MAGIC;
        boolean ITALIC;
        boolean BOLD = ITALIC = MAGIC = STRIKETHROUGH = UNDERLINE = PASSED = false;
        ChatColor textColor = null;
        for (int index = length - 1; index > -1; index--) {
            char section = prefix.charAt(index);
            if ((section == '§') && (index < prefix.length() - 1)) {
                char c = prefix.charAt(index + 1);
                ChatColor color = ChatColor.getByChar(c);
                if (color != null) {
                    if (color.equals(ChatColor.RESET)) {
                        break;
                    }
                    if ((textColor == null) && (color.isFormat())) {
                        if ((color.equals(ChatColor.BOLD)) && (!BOLD)) {
                            BOLD = true;
                        } else if ((color.equals(ChatColor.ITALIC)) && (!ITALIC)) {
                            ITALIC = true;
                        } else if ((color.equals(ChatColor.MAGIC)) && (!MAGIC)) {
                            MAGIC = true;
                        } else if ((color.equals(ChatColor.STRIKETHROUGH)) && (!STRIKETHROUGH)) {
                            STRIKETHROUGH = true;
                        } else if ((color.equals(ChatColor.UNDERLINE)) && (!UNDERLINE)) {
                            UNDERLINE = true;
                        }
                    } else if ((textColor == null) && (color.isColor())) {
                        textColor = color;
                    }
                }
            } else if ((index > 0) && (!PASSED)) {
                char c = prefix.charAt(index);
                char c1 = prefix.charAt(index - 1);
                if ((c != '§') && (c1 != '§') && (c != ' ')) {
                    PASSED = true;
                }
            }
            if ((!PASSED) && (prefix.charAt(index) != ' ')) {
                prefix.deleteCharAt(index);
            }
            if (textColor != null) {
                break;
            }
        }
        String result = suffix.toString().isEmpty() ? "" : getResult(BOLD, ITALIC, MAGIC, STRIKETHROUGH, UNDERLINE, textColor);
        if ((!suffix.toString().isEmpty()) && (!suffix.toString().startsWith("§"))) {
            suffix.insert(0, result);
        }
        return new String[]{prefix.toString().length() > getMaxSize() ? prefix.toString().substring(0, getMaxSize()) : prefix.toString(), suffix.toString().length() > getMaxSize() ? suffix.toString().substring(0, getMaxSize()) : suffix.toString()};
    }

    public int getMaxSize() {
        return 16;
    }
}
