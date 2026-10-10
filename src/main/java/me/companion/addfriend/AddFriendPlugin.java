package me.companion.addfriend;

import org.bukkit.plugin.java.JavaPlugin;

public final class AddFriendPlugin extends JavaPlugin {

    private static AddFriendPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getLogger().info("AddFriend enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AddFriend disabled.");
    }

    public static AddFriendPlugin getInstance() {
        return instance;
    }
}
