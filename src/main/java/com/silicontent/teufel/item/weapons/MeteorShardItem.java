package com.silicontent.teufel.item.weapons;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class MeteorShardItem extends SwordItem {
	public MeteorShardItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (attacker.getRandom().nextFloat() < 0.15) {
			// set the target on fire for 10 seconds
			target.setFireTicks(200);
		}

		return super.postHit(stack, target, attacker);
	}
}
