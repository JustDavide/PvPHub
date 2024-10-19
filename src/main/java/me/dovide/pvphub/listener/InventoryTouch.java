package me.dovide.pvphub.listener;

import me.dovide.pvphub.HubMain;
import me.dovide.pvphub.utils.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryTouch implements Listener {

    private final Config config;

    public InventoryTouch(HubMain instance){
        this.config = instance.getConfig();
    }

    @EventHandler
    public void onInventoryTouch(InventoryClickEvent e){

        Player player = (Player) e.getWhoClicked();

        if(SwitchListener.getEnabledPvP().contains(player)) {
            player.sendMessage(config.getString("msg.no-touch"));
            player.setItemOnCursor(null);
            e.setCancelled(true);
        }

    }

}
