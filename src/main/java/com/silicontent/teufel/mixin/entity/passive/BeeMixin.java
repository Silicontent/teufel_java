package com.silicontent.teufel.mixin.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BeeEntity.class)
public abstract class BeeMixin extends AnimalEntity implements Angerable {
	protected BeeMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * @author Silicontent (mod_id: teufel)
	 * @reason Changes entity attributes to fit with modified behavior.
	 */
	@Overwrite
	public static DefaultAttributeContainer.Builder createBeeAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.85F)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.55F)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
	}

	@Override
	public boolean tryAttack(Entity target) {
		boolean bl = target.damage(this.getDamageSources().sting(this), (float) ((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
		if (bl) {
			this.applyDamageEffects(this, target);
			if (target instanceof LivingEntity) {
				// add to the number of stingers the target has in them
				((LivingEntity)target).setStingerCount(((LivingEntity)target).getStingerCount() + 1);

				// add status effect if the world is not in peaceful mode
				int i = 0;
				if (this.getWorld().getDifficulty() != Difficulty.PEACEFUL) {
					i = 18;
				}
				if (i > 0) {
					((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, i * 10, 0), this);
				}
			}

			this.stopAnger();
			this.playSound(SoundEvents.ENTITY_BEE_STING, 1.0F, 1.0F);
		}

		return bl;
	}
}
