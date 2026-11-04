package com.enderdevelopment.plugin.calender;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Calendar;
import java.util.Date;

public class CalendarListener implements Listener {

    private final Main plugin;

    public CalendarListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Calendar")) {
            return;
        }

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        int day = Integer.parseInt(clickedItem.getItemMeta().getDisplayName().replace("Day ", ""));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.DECEMBER, day);
        Date dayDate = calendar.getTime();
        Date currentDate = new Date();

        if (currentDate.before(dayDate)) {
            player.sendMessage("This day is not yet available.");
            return;
        }

        if (day < 21) {
            player.sendMessage("The calendar is not yet available.");
            return;
        }

        String rewardCommand = plugin.getPluginConfig().getString("days." + day + ".command");
        if (rewardCommand != null && !rewardCommand.isEmpty()) {
            rewardCommand = rewardCommand.replace("%player%", player.getName());
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), rewardCommand);
        }

        ItemStack rewardItem = createRewardItem(day);
        player.getInventory().addItem(rewardItem);
        player.sendMessage("You have claimed your reward for day " + day + "!");
    }

    private ItemStack createRewardItem(int day) {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Reward for Day " + day);
        item.setItemMeta(meta);
        return item;
    }
}