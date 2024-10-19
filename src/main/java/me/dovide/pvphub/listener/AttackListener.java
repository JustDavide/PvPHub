package me.dovide.pvphub.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AttackListener implements Listener {

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e){

        if (!(e.getDamager() instanceof Player) && !(e.getEntity() instanceof Player))
            return;

        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();


        if (!SwitchListener.getEnabledPvP().contains(attacker) || !SwitchListener.getEnabledPvP().contains(victim))
            e.setCancelled(true);

    }

}
