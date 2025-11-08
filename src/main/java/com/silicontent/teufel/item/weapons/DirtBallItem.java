package com.silicontent.teufel.item.weapons;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class DirtBallItem extends SwordItem {
    public DirtBallItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getRandom().nextFloat() < 0.01) {
            // very small chance to slow the enemy
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 2));
        }

        return super.postHit(stack, target, attacker);
    }
}
