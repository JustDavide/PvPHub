package me.dovide.pvphub.commands;

import me.dovide.pvphub.HubMain;
import me.dovide.pvphub.listener.SwitchListener;
import me.dovide.pvphub.utils.Config;
import me.dovide.pvphub.utils.Items;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SwordGive implements CommandExecutor {

    private final HubMain instance;
    private Items items;
    private Config config;
    private SwitchListener SL;

    public SwordGive(HubMain instance){
        this.instance = instance;
        this.items = new Items(instance);
        this.config = instance.getConfig();
        this.SL = new SwitchListener(instance);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("You cannot use this command here");
            return true;
        }

        Player player = (Player) commandSender;

        if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if(!player.hasPermission(config.getString("perm.reload"))){
                player.sendMessage(config.getString("msg.no-perms"));
                return true;
            }
            
            instance.createConfig("config.yml");

            this.config = instance.getConfig();
            this.items = new Items(instance);
            this.SL = new SwitchListener(instance);

            player.sendMessage(config.getString("msg.reload"));
            player.sendMessage(config.getString("msg.sub-reload"));
        }else {
            if (!player.hasPermission(config.getString("perm.getsword"))) {
                player.sendMessage(config.getString("msg.no-perms"));
                return true;
            }

            player.getInventory().addItem(items.pvpSword());
            player.sendMessage(config.getString("msg.sword"));
        }
        return true;
    }
}
