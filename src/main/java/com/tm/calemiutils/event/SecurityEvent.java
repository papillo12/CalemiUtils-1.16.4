package com.tm.calemiutils.event;

import com.tm.calemiutils.security.ISecurity;
import com.tm.calemiutils.tileentity.base.TileEntityCUBase;
import com.tm.api.calemicore.util.Location;
import com.tm.calemiutils.util.helper.SecurityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class SecurityEvent {

    /**
     * Sets the owner of a secured Block when placed.
     */
    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent event) {

        TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());

        //Checks if the Entity is a Player and the Location is a TileEntityBase and implements ISecurity.
        if (event.getEntity() instanceof PlayerEntity && tileEntity instanceof TileEntityCUBase && tileEntity instanceof ISecurity) {

            ISecurity security = (ISecurity) tileEntity;

            security.getSecurityProfile().setOwner((PlayerEntity) event.getEntity());
            ((TileEntityCUBase) tileEntity).markForUpdate();
        }
    }

    /**
     * Prevents Secured Blocks from being broken from other Players.
     */
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {

        Location location = new Location(event.getPlayer().world, event.getPos());

        if (!SecurityHelper.canUseSecuredBlock(location, event.getPlayer(), true)) {
            event.setCanceled(true);
        }
    }

    /**
     * Prevents Secured Blocks from being exploded.
     */
    @SubscribeEvent
    public void onBlockExploded(ExplosionEvent event) {

        List<BlockPos> affectedBlocks = event.getExplosion().getAffectedBlockPositions();

        List<BlockPos> securedBlocksFound = new ArrayList<>();

        for (BlockPos pos : affectedBlocks) {

            TileEntity tileEntity = event.getWorld().getTileEntity(pos);

            if (tileEntity instanceof TileEntityCUBase && tileEntity instanceof ISecurity) {
                securedBlocksFound.add(pos);
            }
        }

        affectedBlocks.removeAll(securedBlocksFound);
    }
}