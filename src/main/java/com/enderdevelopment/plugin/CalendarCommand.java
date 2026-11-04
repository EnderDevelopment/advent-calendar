package com.enderdevelopment.plugin.calender;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class CalendarCommand implements CommandExecutor {

    private final Main plugin;

    public CalendarCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("calendar.use")) {
            player.sendMessage("You do not have permission to use this command.");
            return true;
        }

        openCalendar(player);
        return true;
    }

    private void openCalendar(Player player) {
        Inventory calendar = Bukkit.createInventory(null, 27, "Calendar");
        for (int i = 1; i <= 24; i++) {
            ItemStack dayItem = createDayItem(i);
            calendar.addItem(dayItem);
        }
        player.openInventory(calendar);
    }

    private ItemStack createDayItem(int day) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Day " + day);
        List<String> lore = new ArrayList<>();
        lore.add("Click to claim your reward!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}