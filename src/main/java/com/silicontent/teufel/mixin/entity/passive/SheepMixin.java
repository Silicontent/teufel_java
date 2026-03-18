package com.silicontent.teufel.mixin.entity.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepEntity.class)
public abstract class SheepMixin extends AnimalEntity {
	protected SheepMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * @author Silicontent (mod_id: teufel)
	 * @reason Changes entity attributes to fit with new behavior.
	 */
	@Overwrite
	public static DefaultAttributeContainer.Builder createSheepAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 5.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6F);
	}

	@Inject(method = "initGoals", at = @At("TAIL"))
	public void fleeFromPlayer(CallbackInfo ci) {
		// cause sheep to run away from the player at mach 5
		this.goalSelector.add(1, new FleeEntityGoal<>(this, PlayerEntity.class, 10.0f, 1.0d, 1.5d));
	}

	@ModifyConstant(method = "sheared", constant = @Constant(intValue = 3))
	public int dropOneWool(int val) {
		// modify sheep so that they can only drop one wool when sheared
		return 1;
	}
}
