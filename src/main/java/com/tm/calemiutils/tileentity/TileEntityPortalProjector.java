package com.tm.calemiutils.tileentity;

import com.tm.api.calemicore.tileentity.ITileEntityGuiHandler;
import com.tm.api.calemicore.util.Location;
import com.tm.calemiutils.block.BlockLinkPortal;
import com.tm.calemiutils.block.BlockPortalProjector;
import com.tm.calemiutils.gui.ScreenPortalProjector;
import com.tm.calemiutils.init.InitItems;
import com.tm.calemiutils.init.InitTileEntityTypes;
import com.tm.calemiutils.inventory.ContainerPortalProjector;
import com.tm.calemiutils.item.ItemLinkBookLocation;
import com.tm.calemiutils.tileentity.base.TileEntityInventoryBase;
import com.tm.calemiutils.util.PortalVeinScan;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileEntityPortalProjector extends TileEntityInventoryBase implements ITileEntityGuiHandler {

    public TileEntityPortalProjector() {
        super(InitTileEntityTypes.PORTAL_PROJECTOR.get());
    }

    @Override
    public void tick () {

        if (isProjecting() && !(getLinkBook().getItem() instanceof ItemLinkBookLocation)) {
            stopProjecting();
        }
    }

    public ItemStack getLinkBook() {
        return getInventory().getStackInSlot(0);
    }

    public void teleport(PlayerEntity player) {

        if (isProjecting()) {
            ItemLinkBookLocation.teleport(world, player, ItemLinkBookLocation.getLinkedLocation(world, getLinkBook()), ItemLinkBookLocation.getLinkedRotation(getLinkBook()), ItemLinkBookLocation.getLinkedDimensionName(getLinkBook()), ItemLinkBookLocation.TravelMethod.PORTAL);
        }
    }

    public void startProjecting() {

        if (getLinkBook().getItem() instanceof ItemLinkBookLocation) {

            if (ItemLinkBookLocation.isLinked(getLinkBook())) {

                PortalVeinScan scan = new PortalVeinScan(getLocation().copy(), getBlockState().get(BlockPortalProjector.FACING));
                scan.startPortalScan();

                for (Location air : scan.buffer) {
                    Direction copyDir = getBlockState().get(BlockPortalProjector.FACING);

                    if (air.isAirBlock()) {
                        air.setBlockWithoutNotify(InitItems.LINK_PORTAL.get().getDefaultState().with(BlockLinkPortal.AXIS, copyDir.rotateY().getAxis()));
                        ((TileEntityLinkPortal)air.getTileEntity()).setProjectorLocation(getLocation());
                    }
                }
            }
        }
    }

    public void stopProjecting() {
        getLocation().copy().translate(Direction.UP, 1).setBlockToAir();
    }

    public boolean isProjecting() {
        return getLocation().copy().translate(Direction.UP, 1).getBlock() instanceof BlockLinkPortal;
    }

    @Override
    public int getSizeInventory () {
        return 1;
    }

    @Override
    public ITextComponent getDefaultName () {
        return new StringTextComponent("Portal Projector");
    }

    @Override
    public Container getTileContainer (int windowId, PlayerInventory playerInv) {
        return new ContainerPortalProjector(windowId, playerInv, this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ContainerScreen getTileGuiContainer (int windowId, PlayerInventory playerInv) {
        return new ScreenPortalProjector(getTileContainer(windowId, playerInv), playerInv, getDefaultName());
    }
}
