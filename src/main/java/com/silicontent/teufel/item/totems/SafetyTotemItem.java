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

public class SafetyTotemItem extends Item {
	public SafetyTotemItem(Settings settings) {
		super(settings);
		settings.maxCount(1);
		settings.rarity(Rarity.UNCOMMON);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		// remove the item
		ItemStack heldItem = user.getStackInHand(hand);
		heldItem.decrement(1);

		// apply status effects and deal damage
		user.damage(user.getDamageSources().genericKill(), user.getHealth() - 1.0f);
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 30 * 20));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 30 * 20));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 15 * 20, 2));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30 * 20, 1));
		user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 30 * 20));

		return TypedActionResult.success(user.getStackInHand(hand));
	}
}
