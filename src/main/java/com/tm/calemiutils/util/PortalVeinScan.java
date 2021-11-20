package com.tm.calemiutils.util;

import com.tm.api.calemicore.util.Location;
import com.tm.api.calemicore.util.VeinScan;
import net.minecraft.util.Direction;

public class PortalVeinScan extends VeinScan {

    public static final int MAX_SCAN_SIZE = 625;

    private final Direction orientation;

    public boolean isValidPortal;

    public PortalVeinScan(Location location, Direction orientation) {
        super(location);
        this.orientation = orientation;
    }

    public void startPortalScan () {
        isValidPortal = true;
        scanPortal(new Location(location, Direction.UP));
        scanPortal(new Location(location, orientation.rotateY()));
        scanPortal(new Location(location, orientation.rotateYCCW()));
    }

    public void scanPortal(Location location) {

        if (buffer.size() >= MAX_SCAN_SIZE || !isValidPortal) {
            isValidPortal = false;
            buffer.clear();
            return;
        }

        if (!buffer.contains(location)) {

            if (!location.isAirBlock()) {
                return;
            }

            buffer.add(location);

            scanPortal(new Location(location, Direction.DOWN));
            scanPortal(new Location(location, Direction.UP));
            scanPortal(new Location(location, orientation.rotateY()));
            scanPortal(new Location(location, orientation.rotateYCCW()));
        }
    }
}
