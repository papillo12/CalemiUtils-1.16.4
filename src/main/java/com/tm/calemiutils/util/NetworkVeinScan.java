package com.tm.calemiutils.util;

import com.tm.api.calemicore.util.Location;
import com.tm.api.calemicore.util.VeinScan;
import com.tm.calemiutils.tileentity.base.INetwork;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

public class NetworkVeinScan extends VeinScan {

    public NetworkVeinScan(Location location) {
        super(location);
    }

    public void startNetworkScan (Direction[] directions) {

        for (Direction dir : directions) {
            scanNetwork(new Location(location, dir), dir);
        }
    }

    private void scanNetwork (Location location, Direction oldDir) {

        if (buffer.size() >= MAX_SCAN_SIZE) {
            return;
        }

        TileEntity tileEntity = location.getTileEntity();

        if (tileEntity != null) {

            if (tileEntity instanceof INetwork) {

                INetwork network = (INetwork) tileEntity;

                for (Direction dir : network.getConnectedDirections()) {

                    if (oldDir == dir.getOpposite()) {

                        if (!contains(location)) {

                            buffer.add(location);

                            for (Direction searchDir : network.getConnectedDirections()) {
                                scanNetwork(new Location(location, searchDir), searchDir);
                            }
                        }
                    }
                }
            }
        }
    }
}
