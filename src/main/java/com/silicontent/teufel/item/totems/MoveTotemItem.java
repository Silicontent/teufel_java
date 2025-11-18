package com.silicontent.teufel.item.totems;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MoveTotemItem extends Item {
	public MoveTotemItem(Settings settings) {
		super(settings);
		settings.maxCount(1);
		settings.rarity(Rarity.UNCOMMON);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		// apply the movement status effects
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 180 * 20, 1));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 180 * 20, 2));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300 * 20));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 300 * 20));

		// start the item cooldown
		user.getItemCooldownManager().set(this, 180 * 20);

		return TypedActionResult.success(user.getStackInHand(hand));
	}
}
