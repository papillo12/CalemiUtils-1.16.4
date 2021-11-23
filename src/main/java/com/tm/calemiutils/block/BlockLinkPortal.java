package com.tm.calemiutils.block;

import com.tm.api.calemicore.util.Location;
import com.tm.calemiutils.block.base.BlockBase;
import com.tm.calemiutils.tileentity.TileEntityLinkPortal;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockLinkPortal extends BlockBase implements ITileEntityProvider {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    private static final VoxelShape X_AABB = Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    private static final VoxelShape Z_AABB = Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public BlockLinkPortal() {
        super(Block.Properties.create(Material.PORTAL).sound(SoundType.STONE).doesNotBlockMovement().hardnessAndResistance(-1.0F).setLightLevel((state) -> 11));
        setDefaultState(stateContainer.getBaseState().with(AXIS, Direction.Axis.X));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        Location location = new Location(world, pos);

        if (entity instanceof PlayerEntity && location.getTileEntity() instanceof TileEntityLinkPortal) {

            PlayerEntity player = (PlayerEntity) entity;

            TileEntityLinkPortal portal = (TileEntityLinkPortal) location.getTileEntity();

            if (!player.isPotionActive(Effects.NAUSEA)) portal.teleport((PlayerEntity) entity);
            player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 20, 0));
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos pos, BlockPos facingPos) {

        Location location = new Location((World) worldIn, pos);

        Direction left = (state.get(AXIS) == Direction.Axis.X) ? Direction.WEST : Direction.SOUTH;
        Direction right = (state.get(AXIS) == Direction.Axis.X) ? Direction.EAST : Direction.NORTH;

        if (location.copy().translate(Direction.UP, 1).isAirBlock()) return Blocks.AIR.getDefaultState();
        if (location.copy().translate(Direction.DOWN, 1).isAirBlock()) return Blocks.AIR.getDefaultState();
        if (location.copy().translate(left, 1).isAirBlock()) return Blocks.AIR.getDefaultState();
        if (location.copy().translate(right, 1).isAirBlock()) return Blocks.AIR.getDefaultState();

        return super.updatePostPlacement(state, facing, facingState, worldIn, pos, facingPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(AXIS)) {
            case Z: return Z_AABB;
            case X: default: return X_AABB;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {

        if (rand.nextInt(100) == 0) {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        switch(rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch(state.get(AXIS)) {
                    case Z: return state.with(AXIS, Direction.Axis.X);
                    case X: return state.with(AXIS, Direction.Axis.Z);
                    default: return state;
                }
            default: return state;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityLinkPortal();
    }
}
