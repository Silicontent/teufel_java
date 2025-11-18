package com.silicontent.teufel.item.totems;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PotionTotemItem extends EffectTotemItem {
	// type of effect applied
	StatusEffect statusEffect;
	// effect amplifier
	int amplifier;


	public PotionTotemItem(Settings settings, int cooldownTime, StatusEffect statusEffect, int amplifier) {
		super(settings, cooldownTime);

		// store the effect attributes (cooldownTime is used for effect duration as well)
		this.statusEffect = statusEffect;
		this.amplifier = amplifier;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		// apply the status effect to the user
		user.addStatusEffect(new StatusEffectInstance(statusEffect, cooldownTime, amplifier));

		return super.use(world, user, hand);
	}
}
