package com.ninni.itty_bitty.block;

import com.ninni.itty_bitty.registry.IttyBittyBlockEntityType;
import com.ninni.itty_bitty.registry.IttyBittyItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BugBoxBlock extends BaseEntityBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

    public BugBoxBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (player.isSpectator()) return InteractionResult.CONSUME;

        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof BugBoxBlockEntity bugBoxBlockEntity) {
            if (BugBoxBlock.canOpen(level, blockPos)) {
                if (level.isClientSide) return InteractionResult.SUCCESS;
                player.openMenu(bugBoxBlockEntity);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof BugBoxBlockEntity bugBoxBlockEntity) {
            if (!level.isClientSide && ((player.isCreative() && !bugBoxBlockEntity.isEmpty()) || !player.isCreative())) {
                ItemStack itemStack = IttyBittyItems.BUGBOX.getDefaultInstance();
                blockEntity.saveToItem(itemStack);
                if (bugBoxBlockEntity.hasCustomName()) {
                    itemStack.setHoverName(bugBoxBlockEntity.getCustomName());
                }
                ItemEntity itemEntity = new ItemEntity(level, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
        }
        super.playerWillDestroy(level, blockPos, blockState, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof BugBoxBlockEntity blockEntity1) {
            builder = builder.withDynamicDrop(CONTENTS, consumer -> {
                for (int i = 0; i < blockEntity1.getContainerSize(); ++i) {
                    consumer.accept(blockEntity1.getItem(i));
                }
            });
        }
        return super.getDrops(blockState, builder);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, blockGetter, list, tooltipFlag);
        CompoundTag compoundTag = BlockItem.getBlockEntityData(itemStack);
        int items = 0;

        if (compoundTag != null) {
            if (compoundTag.contains("LootTable", 8)) {
                list.add(Component.translatable("container.bugbox.unknownContents"));
            }
            if (compoundTag.contains("Items", 9)) {
                NonNullList<ItemStack> nonNullList = NonNullList.withSize(54, ItemStack.EMPTY);
                ContainerHelper.loadAllItems(compoundTag, nonNullList);
                for (ItemStack itemStack1 : nonNullList) {
                    if (itemStack1.isEmpty()) continue;
                    items += 1;
                }
                list.add(Component.translatable("container.bugbox.fullness", items, nonNullList.size()).withStyle(ChatFormatting.GREEN));
            }
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        BlockEntity blockEntity;
        if (itemStack.hasCustomHoverName() && (blockEntity = level.getBlockEntity(blockPos)) instanceof BugBoxBlockEntity) {
            ((BugBoxBlockEntity)blockEntity).setCustomName(itemStack.getHoverName());
        }
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
        if (blockEntity instanceof BugBoxBlockEntity) {
            ((BugBoxBlockEntity)blockEntity).recheckOpen();
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (blockState.is(blockState2.getBlock())) return;
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof BugBoxBlockEntity) {
            level.updateNeighbourForOutputSignal(blockPos, blockState.getBlock());
        }
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }


    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        ItemStack itemStack = super.getCloneItemStack(blockGetter, blockPos, blockState);
        blockGetter.getBlockEntity(blockPos, IttyBittyBlockEntityType.BUGBOX).ifPresent(BugBoxBlockEntity -> BugBoxBlockEntity.saveToItem(itemStack));
        return itemStack;
    }

    private static boolean canOpen(Level level, BlockPos blockPos) {
        return !level.getBlockState(blockPos.above()).isSolid();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BugBoxBlockEntity(blockPos, blockState);
    }
}
