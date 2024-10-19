package me.dovide.pvphub.listener;

import me.dovide.pvphub.HubMain;
import me.dovide.pvphub.utils.Config;
import me.dovide.pvphub.utils.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final Items items;
    private final Config config;

    public JoinListener(HubMain instance){
        this.items = new Items(instance);
        this.config = instance.getConfig();
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();
        player.getInventory().setItem(config.getInt("other.slot"), items.pvpSword());

    }

}
