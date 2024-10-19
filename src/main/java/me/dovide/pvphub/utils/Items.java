package me.dovide.pvphub.utils;

import me.dovide.pvphub.HubMain;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

    private final Config config;

    public Items(HubMain instance){
        this.config = instance.getConfig();
    }

    public ItemStack pvpSword(){

        ItemStack i = new ItemStack(Material.valueOf(config.getString("sword.item")), 1);
        ItemMeta meta = i.getItemMeta();

        meta.setDisplayName(config.getString("sword.name"));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.getByName(config.getString("sword.enchant.name")), config.getInt("sword.enchant.lvl"), false);

        i.setItemMeta(meta);

        return i;

    }

    public ItemStack pvpHelmet(){

        ItemStack i = new ItemStack(Material.valueOf(config.getString("helmet.item")), 1);
        ItemMeta meta = i.getItemMeta();

        meta.setDisplayName(config.getString("helmet.name"));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.getByName(config.getString("helmet.enchant.name")), config.getInt("helmet.enchant.lvl"), false);


        i.setItemMeta(meta);

        return i;

    }

    public ItemStack pvpChest(){

        ItemStack i = new ItemStack(Material.valueOf(config.getString("chestplate.item")), 1);
        ItemMeta meta = i.getItemMeta();

        meta.setDisplayName(config.getString("chestplate.name"));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.getByName(config.getString("chestplate.enchant.name")), config.getInt("chestplate.enchant.lvl"), false);


        i.setItemMeta(meta);

        return i;

    }

    public ItemStack pvpLeggings(){

        ItemStack i = new ItemStack(Material.valueOf(config.getString("leggings.item")), 1);
        ItemMeta meta = i.getItemMeta();

        meta.setDisplayName(config.getString("leggings.name"));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.getByName(config.getString("leggings.enchant.name")), config.getInt("leggings.enchant.lvl"), false);


        i.setItemMeta(meta);

        return i;

    }

    public ItemStack pvpBoots(){

        ItemStack i = new ItemStack(Material.valueOf(config.getString("boots.item")), 1);
        ItemMeta meta = i.getItemMeta();

        meta.setDisplayName(config.getString("boots.name"));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.getByName(config.getString("boots.enchant.name")), config.getInt("boots.enchant.lvl"), false);


        i.setItemMeta(meta);

        return i;

    }

    public void clearInventoryExcept(Player player, ItemStack itemToKeep) {
        ItemStack[] inventoryContents = player.getInventory().getContents();

        for (int i = 0; i < inventoryContents.length; i++) {
            ItemStack item = inventoryContents[i];

            if (item != null && !item.isSimilar(itemToKeep)) {
                player.getInventory().setItem(i, null);
            }
        }

        player.updateInventory();
    }


}
