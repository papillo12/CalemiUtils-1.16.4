package com.tm.api.calemicore.util.helper;

import com.tm.api.calemicore.util.Location;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlockState;

public class SoundHelper {

    public static void playAtLocation(Location location, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        location.world.playSound(null, location.getBlockPos(), sound, category, volume, pitch);
    }

    public static void playAtPlayer(PlayerEntity player, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        player.world.playSound(player, player.getPosition(), sound, category, volume, pitch);
    }

    public static void playAtPlayerOnServer(PlayerEntity player, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), sound, category, volume, pitch);
    }

    public static void playSimple(PlayerEntity player, SoundEvent sound) {
        playAtPlayer(player, sound, SoundCategory.PLAYERS, 1, 1);
    }

    public static void playBlockPlace(World world, PlayerEntity player, IForgeBlockState state, Location location) {
        world.playSound(player, location.getBlockPos(), state.getBlockState().getBlock().getSoundType(state.getBlockState(), world, location.getBlockPos(), player).getPlaceSound(), SoundCategory.NEUTRAL, 1.5F, 0.9F);
    }
}
