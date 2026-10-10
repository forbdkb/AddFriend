package me.companion.addfriend;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChatListener implements Listener {

    public static final Set<UUID> awaitingInput = new HashSet<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!awaitingInput.contains(player.getUniqueId())) return;

        event.setCancelled(true);
        awaitingInput.remove(player.getUniqueId());

        String msg = event.getMessage().trim();

        if (msg.equalsIgnoreCase("cancel")) {
            player.sendMessage(ChatColor.RED + "Cancelled.");
            return;
        }

        if (msg.length() < 3 || msg.length() > 16) {
            player.sendMessage(ChatColor.RED + "Invalid name length (3-16 chars).");
            return;
        }

        // ✅ فحص اللاعب: أونلاين أول، بعدها أوفلاين
        String finalName = null;

        Player online = Bukkit.getPlayerExact(msg);
        if (online != null) {
            finalName = online.getName();
        } else {
            OfflinePlayer offline = Bukkit.getOfflinePlayer(msg);
            if (offline != null && offline.hasPlayedBefore() && offline.getName() != null) {
                finalName = offline.getName();
            }
        }

        if (finalName == null) {
            player.sendMessage(ChatColor.RED + "Player '" + msg + "' not found.");
            player.sendMessage(ChatColor.GRAY + "Make sure the name is correct and the player has joined before.");
            return;
        }

        final String nameToSend = finalName;
        player.sendMessage(ChatColor.YELLOW + "Adding " + nameToSend + " to your plot...");

        Bukkit.getScheduler().runTask(AddFriendPlugin.getInstance(), () -> {
            player.performCommand("plots addmember " + nameToSend);
        });
    }
}
