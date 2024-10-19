package me.dovide.brawlhub.listener;

import me.dovide.brawlhub.HubMain;
import me.dovide.brawlhub.utils.Config;
import me.dovide.brawlhub.utils.Items;
import me.dovide.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class SwitchListener implements Listener {

    private final Items items;
    private final HubMain instance;
    private int currentTimer;
    private static final HashMap<Player, ItemStack[]> savedInvs = new HashMap<>();;
    private static final List<Player> enabledPvP = new ArrayList<>();
    private final Config config;
    private final HashMap<Player, BukkitRunnable> activeTasks;

    public SwitchListener(HubMain instance) {
        this.items = new Items(instance);
        this.config = instance.getConfig();
        this.currentTimer = config.getInt("timer");
        this.instance = instance;
        this.activeTasks = new HashMap<>();
    }

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack newItem = player.getInventory().getItem(e.getNewSlot());
        ItemStack previousItem = player.getInventory().getItem(e.getPreviousSlot());

        if (previousItem != null && previousItem.isSimilar(items.pvpSword())) {
            cancelCurrentTask(player);
        }else if(newItem != null && newItem.isSimilar(items.pvpSword())){
            if(enabledPvP.contains(player))
                cancelCurrentTask(player);
        }

        if (newItem != null && newItem.isSimilar(items.pvpSword())) {
            if(!enabledPvP.contains(player)) // The runnable will start only if they aren't already in PVP
                startPvPActivationRunnable(player);
        } else if (newItem != previousItem && enabledPvP.contains(player)) {
            startPvPDeactivationRunnable(player);
        }
    }

    private void cancelCurrentTask(Player player) {
        if (activeTasks.containsKey(player)) {
            activeTasks.get(player).cancel();
            activeTasks.remove(player);
        }
    }

    private void startPvPActivationRunnable(Player player) {
        cancelCurrentTask(player);  // Ensure no existing task is running

        BukkitRunnable activationTask = new BukkitRunnable() {
            int countdown = currentTimer;

            @Override
            public void run() {
                if (countdown > 0) {
                    player.sendMessage(config.getString("msg.timer-on").replace("{seconds}", String.valueOf(countdown)));
                    countdown--;
                } else {
                    player.sendMessage(config.getString("msg.pvp-on"));
                    savedInvs.put(player, player.getInventory().getContents().clone());
                    items.clearInventoryExcept(player, items.pvpSword());
                    player.getInventory().setHelmet(items.pvpHelmet());
                    player.getInventory().setChestplate(items.pvpChest());
                    player.getInventory().setLeggings(items.pvpLeggings());
                    player.getInventory().setBoots(items.pvpBoots());
                    enabledPvP.add(player);
                    currentTimer = config.getInt("other.timer");
                    cancelCurrentTask(player);  // Clear the task list
                    this.cancel();
                }
            }
        };

        activeTasks.put(player, activationTask);
        activationTask.runTaskTimer(instance, 0L, 20L);  // Start the runnable
    }

    private void startPvPDeactivationRunnable(Player player) {
        cancelCurrentTask(player);

        BukkitRunnable deactivationTask = new BukkitRunnable() {
            int countdown = currentTimer;

            @Override
            public void run() {
                if (countdown > 0) {
                    player.sendMessage(config.getString("msg.timer-off").replace("{seconds}", String.valueOf(countdown)));
                    countdown--;
                } else {
                    player.getInventory().clear();
                    player.getInventory().setContents(savedInvs.get(player));
                    enabledPvP.remove(player);
                    savedInvs.remove(player);
                    player.sendMessage(config.getString("msg.pvp-off"));
                    currentTimer = config.getInt("timer");
                    cancelCurrentTask(player);  // Clear the task list
                    this.cancel();
                }
            }
        };

        activeTasks.put(player, deactivationTask);
        deactivationTask.runTaskTimer(instance, 0L, 20L);  // Start the runnable
    }

    public static List<Player> getEnabledPvP(){
        return enabledPvP;
    }

    public static HashMap<Player, ItemStack[]> getSavedInvs(){
        return savedInvs;
    }
}
