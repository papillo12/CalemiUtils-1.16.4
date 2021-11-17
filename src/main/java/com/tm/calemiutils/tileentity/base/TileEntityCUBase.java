package com.tm.calemiutils.tileentity.base;

import com.tm.calemiutils.security.ISecurity;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public abstract class TileEntityCUBase extends TileEntityBase {

    public boolean enable;

    public TileEntityCUBase(final TileEntityType<?> tileEntityType) {
        super(tileEntityType);

        enable = true;
    }

    @Override
    public void read (BlockState state, CompoundNBT nbt) {

        if (this instanceof ISecurity) {

            ISecurity security = (ISecurity) this;
            security.getSecurityProfile().readFromNBT(nbt);
        }

        if (this instanceof ICurrencyNetworkBank) {

            ICurrencyNetworkBank currency = (ICurrencyNetworkBank) this;
            currency.setCurrency(nbt.getInt("currency"));
        }

        enable = nbt.getBoolean("enable");
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write (CompoundNBT nbt) {

        if (this instanceof ISecurity) {

            ISecurity security = (ISecurity) this;
            security.getSecurityProfile().writeToNBT(nbt);
        }

        if (this instanceof ICurrencyNetworkBank) {

            ICurrencyNetworkBank currency = (ICurrencyNetworkBank) this;
            nbt.putInt("currency", currency.getStoredCurrency());
        }

        nbt.putBoolean("enable", enable);
        return super.write(nbt);
    }
}
