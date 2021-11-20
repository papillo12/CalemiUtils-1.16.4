package com.tm.calemiutils.gui;

import com.tm.api.calemicore.gui.ButtonRect;
import com.tm.calemiutils.main.CalemiUtils;
import com.tm.calemiutils.packet.PacketPortalProjector;
import com.tm.calemiutils.tileentity.TileEntityPortalProjector;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ScreenPortalProjector extends ScreenOneSlot {

    private final TileEntityPortalProjector projector;

    private ButtonRect projectButton;

    public ScreenPortalProjector(Container container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        projector = (TileEntityPortalProjector) getTileEntity();
    }

    @Override
    protected void init () {
        super.init();

        if (minecraft != null) {

            projectButton = addButton(new ButtonRect(getScreenX() + 104, getScreenY() + 18, 62, getProjectButtonName(), (btn) -> toggleProjecting()));
        }
    }

    private String getProjectButtonName() {

        if (projector.isProjecting()) {
            return "Projecting";
        }

        return "Disabled";
    }

    private void toggleProjecting() {
        CalemiUtils.network.sendToServer(new PacketPortalProjector(projector.getPos(), !projector.isProjecting()));
    }

    @Override
    public void tick() {
        projectButton.setMessage(new StringTextComponent(getProjectButtonName()));
        super.tick();
    }
}
