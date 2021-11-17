package com.tm.api.calemicore.tileentity;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ITileEntityGuiHandler {

    Container getTileContainer (int windowId, PlayerInventory playerInv);

    @OnlyIn(Dist.CLIENT)
    ContainerScreen getTileGuiContainer (int windowId, PlayerInventory playerInv);
}
