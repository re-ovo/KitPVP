package cc.kinus.kitpvp.commands.subcommands;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.commands.SubCommand;
import cc.kinus.kitpvp.game.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand {
    public JoinCommand() {
        super("join","<竞技场名字>","进入某竞技场");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        if(args.length != 2){
            return;
        }
        Player player = (Player) sender;
        Arena arena = KitPVP.getInstance().getArenaManager().getArenaByName(args[1]);
        if(arena == null){
            sender.sendMessage(ChatColor.RED + "没有此竞技场");
            return;
        }
        sender.sendMessage(ChatColor.GREEN + "加入竞技场完成");
        arena.addPlayer(player);
    }
}
