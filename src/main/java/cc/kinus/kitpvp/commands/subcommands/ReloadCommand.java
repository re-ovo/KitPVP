package cc.kinus.kitpvp.commands.subcommands;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {
    public ReloadCommand() {
        super("reload","","重载插件");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        KitPVP.getInstance().reload();
        sender.sendMessage(ChatColor.GREEN + "重载完成");
    }
}
