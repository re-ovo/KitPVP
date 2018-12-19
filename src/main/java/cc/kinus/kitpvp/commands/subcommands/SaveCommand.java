package cc.kinus.kitpvp.commands.subcommands;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SaveCommand extends SubCommand {
    public SaveCommand() {
        super("save","","保存竞技场数据");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        KitPVP.getInstance().getArenaManager().save();
        sender.sendMessage(ChatColor.GREEN + "保存完成");
    }
}
