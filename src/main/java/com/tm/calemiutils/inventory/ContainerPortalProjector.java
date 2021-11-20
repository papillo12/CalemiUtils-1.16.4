package com.tm.calemiutils.inventory;

import com.tm.calemiutils.init.InitContainerTypes;
import com.tm.calemiutils.inventory.base.ContainerBase;
import com.tm.calemiutils.tileentity.TileEntityPortalProjector;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerPortalProjector extends ContainerBase {

    public ContainerPortalProjector (final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, (TileEntityPortalProjector) getTileEntity(playerInventory, data));
    }

    public ContainerPortalProjector (final int windowId, final PlayerInventory playerInventory, final TileEntityPortalProjector tileEntity) {
        super(InitContainerTypes.PORTAL_PROJECTOR.get(), windowId, playerInventory, tileEntity, 8, 41);
        addSlot(new SlotItemHandler(tileEntity.getInventory(), 0, 80, 18));
    }
}
