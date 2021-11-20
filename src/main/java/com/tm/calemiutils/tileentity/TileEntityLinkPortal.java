package com.tm.calemiutils.tileentity;

import com.tm.api.calemicore.util.Location;
import com.tm.calemiutils.init.InitTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TileEntityLinkPortal extends TileEntity {

    private Location projectorLocation;

    public TileEntityLinkPortal() {
        super(InitTileEntityTypes.LINK_PORTAL.get());
    }

    public void teleport(PlayerEntity player) {

        if (getProjector() != null) {
            getProjector().teleport(player);
        }
    }

    private Location getProjectorLocation() {
        projectorLocation.world = getWorld();
        return projectorLocation;
    }

    public TileEntityPortalProjector getProjector() {

        if (getProjectorLocation() != null && getProjectorLocation().world != null && getProjectorLocation().getTileEntity() != null && getProjectorLocation().getTileEntity() instanceof TileEntityPortalProjector) {
            return (TileEntityPortalProjector) getProjectorLocation().getTileEntity();
        }

        return null;
    }

    public void setProjectorLocation (Location location) {
        projectorLocation = location;
        markDirty();
        world.addBlockEvent(getPos(), getBlockState().getBlock(), 1, 1);
        world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 0);
        world.notifyNeighborsOfStateChange(getPos(), getBlockState().getBlock());
    }

    @Override
    public void onDataPacket (NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(getBlockState(), pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket () {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        int tileEntityType = 64;
        return new SUpdateTileEntityPacket(getPos(), tileEntityType, nbtTagCompound);
    }

    @Override
    public CompoundNBT getUpdateTag () {
        CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return nbt;
    }

    @Override
    public CompoundNBT getTileData () {
        CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return nbt;
    }

    @Override
    public void read (BlockState state, CompoundNBT nbt) {
        projectorLocation = Location.readFromNBT(world, nbt);
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write (CompoundNBT nbt) {
        projectorLocation.writeToNBT(nbt);
        return super.write(nbt);
    }
}
