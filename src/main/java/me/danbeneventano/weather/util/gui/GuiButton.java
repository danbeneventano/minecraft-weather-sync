package me.danbeneventano.weather.util.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class GuiButton extends GuiItem {
    private Consumer<InventoryClickEvent> clickEvent;

    public GuiButton(Material material, String name, Consumer<InventoryClickEvent> clickEvent) {
        super(material, name);
        this.clickEvent = clickEvent;
    }

    public GuiButton(Material material, String name, int amount, Consumer<InventoryClickEvent> clickEvent) {
        super(material, name, amount);
        this.clickEvent = clickEvent;
    }

    public GuiButton(Material material, String name, Consumer<InventoryClickEvent> clickEvent, String... lore) {
        super(material, name, lore);
        this.clickEvent = clickEvent;
    }

    public GuiButton(Material material, String name, int amount, Consumer<InventoryClickEvent> clickEvent, String... lore) {
        super(material, name, amount, lore);
        this.clickEvent = clickEvent;
    }

    public Consumer<InventoryClickEvent> getClickEvent() {
        return clickEvent;
    }

    public void setClickEvent(Consumer<InventoryClickEvent> clickEvent) {
        this.clickEvent = clickEvent;
    }
}
