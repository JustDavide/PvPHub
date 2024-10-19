package me.dovide.pvphub.listener;

import me.dovide.pvphub.HubMain;
import me.dovide.pvphub.utils.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final Config config;

    public DeathListener(HubMain instance){
        this.config = instance.getConfig();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){

        Player player = e.getEntity();

        if(SwitchListener.getEnabledPvP().contains(player)){
            player.getInventory().clear();
            SwitchListener.getEnabledPvP().remove(player);
            player.getInventory().setContents(SwitchListener.getSavedInvs().get(player));
            SwitchListener.getSavedInvs().remove(player);
            if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) player.getLastDamageCause();

                if (damageEvent.getDamager().getType() == EntityType.PLAYER) {
                    Player killer = (Player) damageEvent.getDamager();
                    e.setDeathMessage(config.getString("msg.death-by-player")
                            .replace("{victim}", player.getName())
                            .replace("{killer}", killer.getName()));
                    return;
                }

                e.setDeathMessage(config.getString("msg.death-other")
                        .replace("{victim}", player.getName()));
                return;
            }

            e.setDeathMessage(config.getString("msg.death-other")
                    .replace("{victim}", player.getName()));

        }

    }

}
