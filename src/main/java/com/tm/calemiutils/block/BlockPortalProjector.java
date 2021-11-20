package com.tm.calemiutils.block;

import com.tm.api.calemicore.util.helper.LoreHelper;
import com.tm.calemiutils.block.base.BlockInventoryContainerBase;
import com.tm.calemiutils.init.InitTileEntityTypes;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;

public class BlockPortalProjector extends BlockInventoryContainerBase {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public BlockPortalProjector() {
        super(AbstractBlock.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(3).harvestLevel(2).harvestTool(ToolType.PICKAXE));
        setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public void addInformation (ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        LoreHelper.addInformationLore(tooltip, "Can create portals from Link Books.", true);
        LoreHelper.addControlsLore(tooltip, "Open Inventory", LoreHelper.Type.USE, true);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity (IBlockReader worldIn) {
        return InitTileEntityTypes.PORTAL_PROJECTOR.get().create();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
