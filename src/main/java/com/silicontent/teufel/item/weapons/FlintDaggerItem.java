package com.silicontent.teufel.item.weapons;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class FlintDaggerItem extends SwordItem {
    public FlintDaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

//    private void decrementFireUsage(ItemStack itemStack, PlayerEntity player, ItemUsageContext context) {
//        if (this.fireUses <= 0) {
//            // break the dagger instantly after using its flint and steel ability three times
//            itemStack.damage(99999, player, p -> p.sendToolBreakStatus(context.getHand()));
//        }
//        else {
//            // otherwise, just do regular damage to the dagger
//            itemStack.damage(1, player, p -> p.sendToolBreakStatus(context.getHand()));
//        }
//    }

    // taken directly from FlintAndSteelItem, allows dagger to light items on fire
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // get player, world, and block references
        PlayerEntity playerEntity = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        // light special blocks with "lit" states
        if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
            BlockPos blockPos2 = blockPos.offset(context.getSide());
            if (AbstractFireBlock.canPlaceAt(world, blockPos2, context.getHorizontalPlayerFacing())) {
                world.playSound(playerEntity, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                world.setBlockState(blockPos2, blockState2, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, blockPos);
                ItemStack itemStack = context.getStack();
                // run server code and damage item
                if (playerEntity instanceof ServerPlayerEntity) {
                    Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos2, itemStack);
                    itemStack.damage(15, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
                }

                return ActionResult.success(world.isClient());
            }
            else {
                return ActionResult.FAIL;
            }
        }
        // light any other block on fire
        else {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
            world.setBlockState(blockPos, blockState.with(Properties.LIT, Boolean.TRUE), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            world.emitGameEvent(playerEntity, GameEvent.BLOCK_CHANGE, blockPos);
            if (playerEntity != null) {
                context.getStack().damage(15, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
            }

            return ActionResult.success(world.isClient());
        }
    }
}
