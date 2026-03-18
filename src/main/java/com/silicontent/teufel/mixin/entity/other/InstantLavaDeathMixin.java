package com.silicontent.teufel.mixin.entity.other;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Entity.class)
public class InstantLavaDeathMixin {
	@ModifyConstant(method = "setOnFireFromLava", constant = @Constant(floatValue = 4.0F))
	public float modifyLavaDamage(float val) {
		// NOTE: IntelliJ says this condition is always false, but it works just fine
		//  in-game, so unless there are other entities that need this functionality,
		//  I'll leave it be for now.
		// instantly kill certain entities when they touch lava
		if ((Object) this instanceof PlayerEntity || (Object) this instanceof SheepEntity) {
			return Float.MAX_VALUE;
		}
		return val;
	}
}
