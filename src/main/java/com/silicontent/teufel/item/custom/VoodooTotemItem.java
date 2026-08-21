package com.silicontent.teufel.item.custom;

import com.silicontent.teufel.Teufel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.Objects;
import java.util.UUID;

public class VoodooTotemItem extends Item {
	// TODO: Store this in NBT instead of variable
	private UUID targetID;

	public VoodooTotemItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity instanceof PlayerEntity other) {
			// get the target player's UUID
			targetID = other.getUuid();
		}
		Teufel.LOGGER.info(String.valueOf(targetID));

		return super.useOnEntity(stack, user, entity, hand);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (targetID != null) {
			Objects.requireNonNull(context.getWorld().getPlayerByUuid(targetID)).kill();
		}

		return super.useOnBlock(context);
	}
}
