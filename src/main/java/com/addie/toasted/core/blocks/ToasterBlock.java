package com.addie.toasted.core.blocks;


import com.addie.toasted.core.ToastedModDamageTypes;
import com.addie.toasted.core.ToastedModItems;
import com.addie.toasted.core.ToastedModSounds;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.client.ClientScheduler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class ToasterBlock extends Block implements Waterloggable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5, 1, 5, 11, 7, 11);

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public ToasterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing())
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    // 🔹 When the block is placed, start ticking
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (!world.isClient) {
            world.scheduleBlockTick(pos, this, 20); // tick in 1 second
        }
    }

    // 🔹 Also reschedule ticking when neighbors update (like water spreading)
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos,
                               Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        if (!world.isClient) {
            world.scheduleBlockTick(pos, this, 20);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(WATERLOGGED)) {
            List<PlayerEntity> players = world.getEntitiesByClass(
                    PlayerEntity.class,
                    new Box(pos).expand(1.5),
                    LivingEntity::isAlive
            );

            for (PlayerEntity player : players) {
                player.damage(ToastedModDamageTypes.toaster(world), Float.MAX_VALUE);
            }
        }

        // 🔹 Keep ticking forever
        world.scheduleBlockTick(pos, this, 20); // every second
        super.scheduledTick(state, world, pos, random);
    }

    public static DamageType registerDamageType(RegistryKey<DamageType> key, DamageType damageType) {
        return damageType;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player,
                              Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (player.getStackInHand(hand).getItem() == Items.BREAD) {
                player.getStackInHand(hand).decrement(1);
                world.playSound(null, pos, ToastedModSounds.TOASTER, net.minecraft.sound.SoundCategory.BLOCKS, 1.0F, 1.5F);
                world.playSound(null, pos, ToastedModSounds.TOASTER_ACTIVE, net.minecraft.sound.SoundCategory.BLOCKS, 1.0F, 1.0F);

                ClientScheduler.get().runTaskLater(() -> {
                    ItemStack toastItem = new ItemStack(ToastedModItems.TOAST);
                    Vec3d spawnPosition = Vec3d.ofCenter(pos).add(0, 0.4, 0);
                    ItemEntity toastEntity = new ItemEntity(world, spawnPosition.x, spawnPosition.y, spawnPosition.z, toastItem);
                    world.spawnEntity(toastEntity);
                    world.playSound(null, pos, ToastedModSounds.TOASTER, net.minecraft.sound.SoundCategory.BLOCKS, 1.0F, 1.0F);
                }, TimeUnit.SECONDS, 10);

                return ActionResult.SUCCESS;
            }

            if (player.getStackInHand(hand).getItem() == ToastedModItems.TOAST) {
                player.getStackInHand(hand).decrement(1);
                world.playSound(null, pos, ToastedModSounds.TOASTER, net.minecraft.sound.SoundCategory.BLOCKS, 1.0F, 1.5F);
                world.playSound(null, pos, ToastedModSounds.TOASTER_ACTIVE, net.minecraft.sound.SoundCategory.BLOCKS, 1.0F, 1.0F);

                ClientScheduler.get().runTaskLater(() -> {
                    ItemStack toastItem = new ItemStack(ToastedModItems.BURNT_TOAST);
                    Vec3d spawnPosition = Vec3d.ofCenter(pos).add(0, 0.4, 0);
                    ItemEntity toastEntity = new ItemEntity(world, spawnPosition.x, spawnPosition.y, spawnPosition.z, toastItem);
                    world.spawnEntity(toastEntity);
                    world.playSound(null, pos, ToastedModSounds.TOASTER, net.minecraft.sound.SoundCategory.BLOCKS, 1.0F, 1.0F);
                }, TimeUnit.SECONDS, 10);

                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
