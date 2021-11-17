package com.tm.calemiutils.util;

import com.tm.calemiutils.tileentity.TileEntityBank;
import com.tm.api.calemicore.util.Location;

public class NetworkHelper {

    public static TileEntityBank getConnectedBank (Location unitLocation, Location bankLocation) {

        if (bankLocation != null && bankLocation.getTileEntity() instanceof TileEntityBank) {

            TileEntityBank bank = (TileEntityBank) bankLocation.getTileEntity();

            if (bank.enable) {

                if (bank.connectedUnits.contains(unitLocation)) {
                    return bank;
                }
            }
        }

        return null;
    }
}
