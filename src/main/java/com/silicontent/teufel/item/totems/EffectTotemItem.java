package com.silicontent.teufel.item.totems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EffectTotemItem extends Item {
	int cooldownTime;


	public EffectTotemItem(Settings settings, int cooldownTime) {
		super(settings);
		settings.maxCount(1);
		settings.rarity(Rarity.UNCOMMON);
		this.cooldownTime = cooldownTime;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		// start the item cooldown
		user.getItemCooldownManager().set(this, cooldownTime);

		return TypedActionResult.success(user.getStackInHand(hand));
	}
}
