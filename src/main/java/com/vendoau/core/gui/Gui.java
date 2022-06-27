package com.vendoau.core.gui;

import com.vendoau.core.CoreExtension;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Gui {

    protected final Inventory inventory;

    private final Map<ItemStack, Consumer<Player>> actionMap = new HashMap<>();

    public Gui(@NotNull Inventory inventory) {
        this.inventory = inventory;

        CoreExtension.get().eventNode().addListener(InventoryPreClickEvent.class, event -> {
            final Inventory inv = event.getInventory();
            if (inv == null || !inv.equals(inventory)) return;

            event.setCancelled(true);
            onClick(event);
        });
    }

    public void open(@NotNull Player player) {
        player.openInventory(inventory);
    }

    protected void addAction(@NotNull ItemStack item, @NotNull Consumer<Player> action) {
        actionMap.put(item, action);
    }

    protected void onClick(@NotNull InventoryPreClickEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getClickedItem();
        if (actionMap.containsKey(item)) {
            actionMap.get(item).accept(player);
        }
    }

    @NotNull
    protected ItemStack newItem(@NotNull Material material, @NotNull String name) {
        final Component component = Component.text(name, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        return ItemStack.of(material).withDisplayName(component);
    }
}
