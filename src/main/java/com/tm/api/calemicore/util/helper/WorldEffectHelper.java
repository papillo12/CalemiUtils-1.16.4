package com.tm.api.calemicore.util.helper;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WorldEffectHelper {

    /**
     * @param effectOnly Does lighting cause entity damage.
     */
    public static void spawnLightning(World world, double x, double y, double z, boolean effectOnly) {

        if (!world.isRemote()){
            ServerWorld serverWorld = (ServerWorld) world;

            LightningBoltEntity bolt = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
            bolt.setPosition(x, y, z);
            bolt.setEffectOnly(effectOnly);
            serverWorld.addEntity(bolt);
        }
    }

    public static void startRain(World world, int durationTicks, boolean isStorm) {

        if (!world.isRemote) {

            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.func_241113_a_(0, durationTicks, true, isStorm);
        }
    }
}
