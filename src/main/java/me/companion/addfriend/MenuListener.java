package me.companion.addfriend;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MenuListener implements Listener {

    private static final String MENU_KEYWORD = "plots";
    private static final int SLOT = 26;

    @EventHandler
    public void onMenuOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        String title = ChatColor.stripColor(event.getView().getTitle()).toLowerCase();
        if (!title.contains(MENU_KEYWORD)) return;

        Inventory inv = event.getInventory();
        if (inv.getSize() <= SLOT) return;

        ItemStack current = inv.getItem(SLOT);
        if (current != null && current.getType() != Material.AIR) return;

        ItemStack button = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = button.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Add Friend");
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Click to add a friend",
                    ChatColor.GRAY + "to your plot."
            ));
            button.setItemMeta(meta);
        }

        Bukkit.getScheduler().runTaskLater(AddFriendPlugin.getInstance(), () -> {
            try {
                player.getOpenInventory().getTopInventory().setItem(SLOT, button);
                player.updateInventory();
            } catch (Exception ignored) {}
        }, 2L);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        String title = ChatColor.stripColor(event.getView().getTitle()).toLowerCase();
        if (!title.contains(MENU_KEYWORD)) return;

        if (event.getRawSlot() != SLOT) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() != Material.PLAYER_HEAD) return;

        event.setCancelled(true);
        player.closeInventory();

        player.sendMessage(ChatColor.GREEN + "Type the player name in chat:");
        player.sendMessage(ChatColor.GRAY + "(Type 'cancel' to cancel)");
        ChatListener.awaitingInput.add(player.getUniqueId());
    }
}
