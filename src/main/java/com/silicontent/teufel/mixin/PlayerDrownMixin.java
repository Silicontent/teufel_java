package com.silicontent.teufel.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LivingEntity.class)
public abstract class PlayerDrownMixin extends Entity {
	public PlayerDrownMixin(EntityType<?> type, World world) {
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

	@Override
	public int getMaxAir() {
		// decrease the time before the player begins drowning underwater
		if ((Object) this instanceof PlayerEntity) {
			return 0;
		}
		return super.getMaxAir();
	}
}
