package com.silicontent.teufel.item.totems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DeathTotemItem extends EffectTotemItem {
	public DeathTotemItem(Settings settings, int cooldownTime) {
		super(settings, cooldownTime);
		settings.maxDamage(1);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		// remove the item
		ItemStack heldItem = user.getStackInHand(hand);
		heldItem.decrement(1);
		// yeah
		user.kill();

		return super.use(world, user, hand);
	}
}
