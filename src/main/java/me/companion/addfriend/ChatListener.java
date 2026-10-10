package me.companion.addfriend;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
            player.sendMessage(ChatColor.RED + "Invalid name.");
            return;
        }

        Bukkit.getScheduler().runTask(AddFriendPlugin.getInstance(), () -> {
            Bukkit.dispatchCommand(player, "plots addmember " + msg);
        });
    }
}
