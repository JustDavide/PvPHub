package me.dovide.brawlhub.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e){

        Player player = e.getPlayer();

        if (SwitchListener.getEnabledPvP().contains(player)){
            player.getInventory().clear();
            player.getInventory().setContents(SwitchListener.getSavedInvs().get(player));
            SwitchListener.getSavedInvs().remove(player);
            SwitchListener.getEnabledPvP().remove(player);
        }

    }

}
