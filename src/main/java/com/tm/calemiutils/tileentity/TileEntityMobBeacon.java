package com.tm.calemiutils.tileentity;

import com.tm.calemiutils.init.InitTileEntityTypes;
import com.tm.calemiutils.tileentity.base.TileEntityCUBase;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMobBeacon extends TileEntity {

    public TileEntityMobBeacon () {
        super(InitTileEntityTypes.MOB_BEACON.get());
    }
}
