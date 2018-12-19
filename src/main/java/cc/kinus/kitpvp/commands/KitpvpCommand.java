package cc.kinus.kitpvp.commands;

import cc.kinus.kitpvp.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class KitpvpCommand implements CommandExecutor {
    private List<SubCommand> subCommandList;

    public KitpvpCommand() {
        this.subCommandList = new ArrayList<>();

        this.subCommandList.add(new JoinCommand());
        this.subCommandList.add(new SetLobbyCommand());
        this.subCommandList.add(new CreateArenaCommand());
        this.subCommandList.add(new SetLocCommand());
        this.subCommandList.add(new SaveCommand());
        this.subCommandList.add(new ReloadCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("kitpvp.admin")){
            sender.sendMessage("[KitPVP] KitPVP ~ Code by RE");
            return true;
        }
        if(args.length == 0){
            sender.sendMessage("§6=========================== §a命令帮助 §6===========================");
            for(SubCommand subCommand : this.subCommandList){
                sender.sendMessage(
                        String.format("§a/kitpvp %s §2%s §7- §b%s",subCommand.getName(),subCommand.getArgs(),subCommand.getDescription())
                );
            }
        }else{
            for(SubCommand subCommand : this.subCommandList){
                if(subCommand.getName().equalsIgnoreCase(args[0])){
                    subCommand.execute(sender,args);
                    break;
                }
            }
        }
        return true;
    }

    public List<SubCommand> getSubCommandList() {
        return subCommandList;
    }
}
