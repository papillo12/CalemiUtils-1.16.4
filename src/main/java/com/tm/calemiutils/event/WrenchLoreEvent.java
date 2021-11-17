package com.tm.calemiutils.event;

import com.tm.api.calemicore.util.helper.ItemHelper;
import com.tm.api.calemicore.util.helper.LoreHelper;
import com.tm.calemiutils.util.helper.CurrencyHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WrenchLoreEvent {

    /**
     * Handles adding Lore to an Item storing currency.
     */
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onLoreEvent (ItemTooltipEvent event) {

        if (event.getItemStack().getTag() != null) {

            int currency = ItemHelper.getNBT(event.getItemStack()).getInt("currency");

            if (currency != 0) {
                event.getToolTip().add(new StringTextComponent(""));
                CurrencyHelper.addCurrencyLore(event.getToolTip(), currency);
            }
        }
    }
}
