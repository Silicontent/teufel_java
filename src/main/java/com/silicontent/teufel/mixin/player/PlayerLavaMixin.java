package com.silicontent.teufel.mixin.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Entity.class)
public class PlayerLavaMixin {
	@ModifyConstant(method = "setOnFireFromLava", constant = @Constant(floatValue = 4.0F))
	public float modifyLavaDamage(float val) {
		// instantly kill the player when they touch lava
		if ((Object) this instanceof PlayerEntity) {
			return Float.MAX_VALUE;
		}
		return val;
	}
}
