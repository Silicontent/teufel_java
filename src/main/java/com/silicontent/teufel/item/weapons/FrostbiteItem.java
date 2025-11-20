package com.silicontent.teufel.item.weapons;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class FrostbiteItem extends SwordItem {
	public FrostbiteItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (attacker.getRandom().nextFloat() < 0.25) {
			// inflict extra freezing damage on the target
			target.damage(target.getDamageSources().freeze(), 2.0f);
			// give the target slowness
			target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 0), attacker);
		}

		return super.postHit(stack, target, attacker);
	}
}
