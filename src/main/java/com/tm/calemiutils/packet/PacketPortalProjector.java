package com.tm.calemiutils.packet;

import com.tm.api.calemicore.util.Location;
import com.tm.calemiutils.tileentity.TileEntityPortalProjector;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketPortalProjector {

    private BlockPos pos;
    private boolean project;

    public PacketPortalProjector () {}

    public PacketPortalProjector (BlockPos pos, boolean project) {
        this.pos = pos;
        this.project = project;
    }

    public PacketPortalProjector (PacketBuffer buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        project = buf.readBoolean();
    }

    public void toBytes (PacketBuffer buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeBoolean(project);
    }

    public void handle (Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            ServerPlayerEntity player = ctx.get().getSender();

            if (player != null) {

                Location location = new Location(player.world, pos);

                if (location.getTileEntity() instanceof TileEntityPortalProjector) {

                    TileEntityPortalProjector projector = (TileEntityPortalProjector) location.getTileEntity();

                    if (project) {
                        projector.startProjecting();
                    }

                    else projector.stopProjecting();
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
