package com.tm.api.calemicore.util.helper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class EffectHelper {

    public static void addPotionEffect(Effect effect, int durationTicks, int amplifier, LivingEntity... players) {

        for (LivingEntity player : players) {

            if (!(player instanceof PlayerEntity) || !((PlayerEntity)player).isCreative()) {
                player.removeActivePotionEffect(effect);
                player.addPotionEffect(new EffectInstance(effect, durationTicks, amplifier, true, false));
            }
        }
    }

    /**
     * Infinite potion effects last until they are removed.
     */
    public static void addInfinitePotionEffect(Effect effect, int amplifier, PlayerEntity... players) {
        addPotionEffect(effect, 1000000, amplifier, players);
    }
}

