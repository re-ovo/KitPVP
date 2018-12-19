package cc.kinus.kitpvp.commands.subcommands;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.commands.SubCommand;
import cc.kinus.kitpvp.config.configs.ArenaConfig;
import cc.kinus.kitpvp.config.configs.MainConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLobbyCommand extends SubCommand {
    public SetLobbyCommand() {
        super("setlobby", "", "设置总大厅位置");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)){
           return;
        }
        Player player = (Player) sender;
        ArenaConfig mainConfig = KitPVP.getInstance().getConfigManager().getArenaConfig();

        mainConfig.getConfiguration().set("spawn",player.getLocation().clone());

        player.sendMessage(ChatColor.GREEN + "设置主大厅位置完成！");
    }
}
