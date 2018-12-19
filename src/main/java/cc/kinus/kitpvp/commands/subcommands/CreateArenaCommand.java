package cc.kinus.kitpvp.commands.subcommands;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.commands.KitpvpCommand;
import cc.kinus.kitpvp.commands.SubCommand;
import cc.kinus.kitpvp.game.Arena;
import cc.kinus.kitpvp.game.ArenaManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CreateArenaCommand extends SubCommand {
    public CreateArenaCommand() {
        super("create", "<名字>", "创建一个竞技场");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            return;
        }
        // 创建一个竞技场
        Arena arena = new Arena(args[1], null, null, null, null, null);
        // 获取竞技场管理器
        ArenaManager arenaManager = KitPVP.getInstance().getArenaManager();
        // 添加此竞技场，并且保存
        arenaManager.getArenas().add(arena);
        arenaManager.save();
        // done
        sender.sendMessage(ChatColor.GREEN + "竞技场" + arena.getName() + "创建完成");
    }
}
