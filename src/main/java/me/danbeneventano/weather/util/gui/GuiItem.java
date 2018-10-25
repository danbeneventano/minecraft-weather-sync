package me.danbeneventano.weather.util.gui;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class GuiItem {
    private Material material;
    private int amount = 1;
    private String name;
    private List<String> lore = Lists.newArrayList();
    private boolean enchanted = false;
    private ItemStack itemStack;

    public GuiItem(Material material, String name) {
        this.material = material;
        this.name = name;
    }

    public GuiItem(Material material, String name, int amount) {
        this.material = material;
        this.name = name;
        this.amount = amount;
    }

    public GuiItem(Material material, String name, String... lore) {
        this.material = material;
        this.name = name;
        this.lore.addAll(Arrays.asList(lore));
    }

    public GuiItem(Material material, String name, int amount, String... lore) {
        this.material = material;
        this.name = name;
        this.amount = amount;
        this.lore.addAll(Arrays.asList(lore));
    }

    private void generateItemStack() {
        this.itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if (enchanted) {
            itemStack.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
        }
    }

    public void setEnchanted(boolean enchanted) {
        this.enchanted = enchanted;
        generateItemStack();
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
