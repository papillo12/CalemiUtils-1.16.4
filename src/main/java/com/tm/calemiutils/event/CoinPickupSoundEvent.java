package com.tm.calemiutils.event;

import com.tm.calemiutils.init.InitSounds;
import com.tm.calemiutils.item.ItemCoin;
import com.tm.api.calemicore.util.helper.SoundHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CoinPickupSoundEvent {

    /**
     * Handles adding sound effect when coin is picked up.
     */
    @SubscribeEvent
    public void onItemPickup (PlayerEvent.ItemPickupEvent event) {

        if (event.getEntity() instanceof PlayerEntity) {

            if (event.getStack().getItem() instanceof ItemCoin) {

                SoundHelper.playSound(event.getPlayer(), InitSounds.COIN.get(), SoundCategory.PLAYERS, 0.1F, 1F);
            }
        }
    }
}
