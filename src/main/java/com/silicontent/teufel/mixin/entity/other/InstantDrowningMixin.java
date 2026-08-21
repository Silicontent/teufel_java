package com.silicontent.teufel.mixin.entity.other;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LivingEntity.class)
public abstract class InstantDrowningMixin extends Entity {
	@Shadow
	public abstract ItemStack getMainHandStack();

	public InstantDrowningMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@ModifyConstant(method = "baseTick", constant = @Constant(intValue = -20))
	public int modifyDrowningLimit(int val) {
		// decrease the time between damage ticks while drowning
		if ((Object) this instanceof PlayerEntity) {
			return -1;
		}
		return val;
	}

	@ModifyConstant(method = "baseTick", constant = @Constant(floatValue = 2.0F))
	public float modifyDrowningDamage(float val) {
		float dmg = val;

		// only apply extra drown damage to player
		// also gets entity as PlayerEntity, creates player variable for later use
		if ((Object) this instanceof PlayerEntity player) {
//			checks if the player is holding a specific item to remove drowing effect
//			works similarly to a Turtle Shell
//			if (player.getMainHandStack().isOf(Items.DIAMOND)) {
//				dmg = 0.0f;
//			}
//			else {
//				dmg = 10.0f;
//			}

			dmg = 4.0f;
		}
		return dmg;
	}

	@Override
	public int getMaxAir() {
		// decrease the time before the player begins drowning underwater
		if ((Object) this instanceof PlayerEntity) {
			return 0;
		}
		return super.getMaxAir();
	}
}
