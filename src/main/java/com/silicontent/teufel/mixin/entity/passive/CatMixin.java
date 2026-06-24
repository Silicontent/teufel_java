package com.silicontent.teufel.mixin.entity.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CatEntity.class)
public abstract class CatMixin extends TameableEntity {
	protected CatMixin(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * @author Silicontent (mod_id: teufel)
	 * @reason Changes attributes to fit with modified behavior.
	 */
	@Overwrite
	public static DefaultAttributeContainer.Builder createCatAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32F)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0);
	}
}