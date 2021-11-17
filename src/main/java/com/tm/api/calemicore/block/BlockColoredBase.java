package com.tm.api.calemicore.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;

/**
 * The base class for colored Blocks.
 */
public class BlockColoredBase extends Block {

    //Property used to define the block's color value.
    public static final EnumProperty<DyeColor> COLOR = CCBlockStates.COLOR;

    /**
     * @param properties The specific properties for the Block. (Creative Tab, hardness, material, etc.)
     */
    protected BlockColoredBase (Properties properties) {
        super(properties);
        setDefaultState(this.stateContainer.getBaseState().with(COLOR, DyeColor.BLUE));
    }

    @Override
    protected void fillStateContainer (StateContainer.Builder<Block, BlockState> builder) {
        builder.add(COLOR);
    }
}
