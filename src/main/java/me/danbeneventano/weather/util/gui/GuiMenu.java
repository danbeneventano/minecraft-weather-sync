package me.danbeneventano.weather.util.gui;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.danbeneventano.weather.WeatherPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GuiMenu implements Listener {
    private String title;
    private int rows;
    private Inventory inventory;
    private BiMap<Integer, GuiItem> map = HashBiMap.create();
    private boolean cancelClicks;

    public GuiMenu(String title, int rows, boolean cancelClicks) {
        this.title = title;
        this.rows = rows;
        this.inventory = Bukkit.createInventory(null, rows * 9 - 1, title);
        this.cancelClicks = cancelClicks;
        Bukkit.getServer().getPluginManager().registerEvents(this, WeatherPlugin.instance());
    }

    public void set(int slot, GuiItem item) {
        map.put(slot, item);
        inventory.setItem(slot, item.getItemStack());
    }

    public void add(GuiItem item) {
        int slot = 0;
        while (inventory.getItem(slot) != null) {
            slot++;
        }
        if (slot > rows * 9 - 1) return;
        inventory.setItem(slot, item.getItemStack());
        map.put(slot, item);
    }

    public void delete(int slot) {
        inventory.setItem(slot, null);
        map.remove(slot);
    }

    public void delete(GuiItem item) {
        int slot = map.inverse().remove(item);
        inventory.setItem(slot, null);
    }

    public void clear() {
        map.clear();
        inventory.clear();
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != inventory) return;
        int slot = event.getSlot();
        if (!map.containsKey(slot)) return;
        if (!(map.get(slot) instanceof GuiButton)) return;
        if (cancelClicks) event.setCancelled(true);
        ((GuiButton) map.get(slot)).getClickEvent().accept(event);
    }
}
