package com.tm.calemiutils.tileentity.base;

import com.tm.api.calemicore.util.Location;

public interface ICurrencyNetworkUnit extends INetwork {

    Location getBankLocation ();

    void setBankLocation (Location location);
}
