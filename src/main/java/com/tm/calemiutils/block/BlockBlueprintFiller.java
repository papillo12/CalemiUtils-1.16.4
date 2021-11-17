package com.tm.calemiutils.block;

import com.tm.calemiutils.block.base.BlockInventoryContainerBase;
import com.tm.calemiutils.init.InitTileEntityTypes;
import com.tm.api.calemicore.util.helper.LoreHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

public class BlockBlueprintFiller extends BlockInventoryContainerBase {

    public BlockBlueprintFiller () {
        super(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2));
    }

    @Override
    public void addInformation (ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        LoreHelper.addInformationLore(tooltip, "Places mass amounts of blocks in Blueprint.", true);
        LoreHelper.addInformationLore(tooltip, "Uses blocks from any inventory above it.");
        LoreHelper.addControlsLore(tooltip, "Open Inventory", LoreHelper.Type.USE, true);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity (IBlockReader worldIn) {
        return InitTileEntityTypes.BLUEPRINT_FILLER.get().create();
    }
}
