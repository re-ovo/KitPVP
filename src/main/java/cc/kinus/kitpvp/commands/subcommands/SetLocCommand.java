package cc.kinus.kitpvp.commands.subcommands;

import cc.kinus.kitpvp.KitPVP;
import cc.kinus.kitpvp.commands.SubCommand;
import cc.kinus.kitpvp.game.Arena;
import cc.kinus.kitpvp.utils.LookUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SetLocCommand extends SubCommand {
    public SetLocCommand() {
        super("set", "<竞技场> <位置>", "设置竞技场point(spawn/loc12/pro12)");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        if(args.length != 3){
            return;
        }
        Player player = (Player) sender;
        Arena arena = KitPVP.getInstance().getArenaManager().getArenaByName(args[1]);
        String type = args[2];
        if(arena == null){
            sender.sendMessage("没有此竞技场: "+args[1]);
            return;
        }
        switch (type){
            case "spawn":{
                arena.setSpawn(player.getLocation().clone());
                sender.sendMessage("设置 "+type+" 完成");
                break;
            }
            case "loc1":{
                arena.setLoc1(Objects.requireNonNull(LookUtil.getPlayerLookingBlock(player)).getLocation().clone());
                sender.sendMessage("设置 "+type+" 完成");
                break;
            }
            case "loc2":{
                arena.setLoc2(Objects.requireNonNull(LookUtil.getPlayerLookingBlock(player)).getLocation().clone());
                sender.sendMessage("设置 "+type+" 完成");
                break;
            }
            case "pro1":{
                arena.setProtect1(Objects.requireNonNull(LookUtil.getPlayerLookingBlock(player)).getLocation().clone());
                sender.sendMessage("设置 "+type+" 完成");
                break;
            }
            case "pro2":{
                arena.setProtect2(Objects.requireNonNull(LookUtil.getPlayerLookingBlock(player)).getLocation().clone());
                sender.sendMessage("设置 "+type+" 完成");
                break;
            }
            default:{
                sender.sendMessage(ChatColor.RED + "Unknown Point Type : "+type);
            }
        }
    }
}
