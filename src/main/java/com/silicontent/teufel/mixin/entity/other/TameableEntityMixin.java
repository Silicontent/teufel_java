package com.silicontent.teufel.mixin.entity.other;

import com.silicontent.teufel.Teufel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TameableEntity.class)
public abstract class TameableEntityMixin extends LivingEntity {
	protected TameableEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "setTamed", at = @At("TAIL"))
	public void increaseHealth(boolean tamed, CallbackInfo ci) {
		// TODO: increases the health of entities when they are tamed
		if (tamed) {
			EntityAttributeInstance maxHealth = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
			assert maxHealth != null;
			maxHealth.addPersistentModifier(
					new EntityAttributeModifier(
							Teufel.MOD_ID + ":tamed_health_increase",
							50,
							EntityAttributeModifier.Operation.ADDITION
					)
			);
		}
	}
}
