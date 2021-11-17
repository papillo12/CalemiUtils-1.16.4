package com.tm.api.calemicore.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

/**
 * The base class for Items that place Blocks.
 */
public class BlockItemBase extends BlockItem {

    public BlockItemBase(Block block, ItemGroup tab) {
        super(block , new Properties().group(tab));
    }

    public BlockItemBase(Block block) {
        super(block , new Properties());
    }
}
