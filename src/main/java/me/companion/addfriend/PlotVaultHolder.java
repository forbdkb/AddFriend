package me.companion.addfriend;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class PlotVaultHolder implements InventoryHolder {

    private final UUID ownerUuid;
    private final UUID viewerUuid;
    private Inventory inventory;
    private boolean isReadOnly;

    public PlotVaultHolder(UUID ownerUuid, UUID viewerUuid) {
        this.ownerUuid = ownerUuid;
        this.viewerUuid = viewerUuid;
        this.isReadOnly = false;
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    public UUID getViewerUuid() {
        return viewerUuid;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
