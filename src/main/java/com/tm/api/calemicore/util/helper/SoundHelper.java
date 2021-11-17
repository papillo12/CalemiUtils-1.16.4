package com.tm.api.calemicore.util.helper;

import com.tm.api.calemicore.util.Location;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlockState;

public class SoundHelper {

    public static void playSoundAtLocation(Location location, SoundEvent sound, SoundCategory category, float volume, float pitch) {

        if (location.world.isRemote()) {
            location.world.playSound(null, location.getBlockPos(), sound, category, volume, pitch);
        }

        else location.world.playSound(null, location.getBlockPos().getX(), location.getBlockPos().getY(), location.getBlockPos().getZ(), sound, category, 1, 1);
    }

    public static void playSound(PlayerEntity player, SoundEvent sound, SoundCategory category, float volume, float pitch) {

        if (player.world.isRemote()) {
            player.world.playSound(player, player.getPosition(), sound, category, volume, pitch);
        }

        else player.world.playSound(null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), sound, category, 1, 1);
    }

    public static void playSimpleSound(PlayerEntity player, SoundEvent sound) {
        playSound(player, sound, SoundCategory.PLAYERS, 1, 1);
    }

    public static void playBlockPlaceSound (World world, PlayerEntity player, IForgeBlockState state, Location location) {
        world.playSound(player, location.getBlockPos(), state.getBlockState().getBlock().getSoundType(state.getBlockState(), world, location.getBlockPos(), player).getPlaceSound(), SoundCategory.NEUTRAL, 1.5F, 0.9F);
    }
}
