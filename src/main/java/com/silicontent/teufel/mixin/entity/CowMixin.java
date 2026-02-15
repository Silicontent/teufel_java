package com.silicontent.teufel.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CowEntity.class)
public abstract class CowMixin extends AnimalMixin {
	protected CowMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * @author Silicontent (mod_id: teufel)
	 * @reason Changes cow attributes to fit with new hostile behavior.
	 */
	@Overwrite
	public static DefaultAttributeContainer.Builder createCowAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.1)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0);
	}

	@Override
	public void populateGoals() {
		// populate goal lists
		this.getCommonGoals().add(new PrioritizedGoal(0, new SwimGoal(this)));
		this.getCommonGoals().add(new PrioritizedGoal(2, new WanderAroundFarGoal(this, 1.0)));
		this.getCommonGoals().add(new PrioritizedGoal(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f)));
		this.getCommonGoals().add(new PrioritizedGoal(4, new LookAroundGoal(this)));
		this.getCommonGoals().add(new PrioritizedGoal(4, new FollowParentGoal((AnimalEntity) (Object) this, 1.25)));
		this.getHostileGoals().add(new PrioritizedGoal(1, new MeleeAttackGoal(this, 1.0, false)));
		this.getPassiveGoals().add(new PrioritizedGoal(1, new EscapeDangerGoal(this, 2.0)));
		this.getPassiveGoals().add(new PrioritizedGoal(2, new AnimalMateGoal((AnimalEntity) (Object) this, 1.0)));
		this.getPassiveGoals().add(new PrioritizedGoal(3, new TemptGoal(this, 1.25, Ingredient.ofItems(Items.WHEAT), false)));

		// adds player as target for cow
		this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	public void initGoals() {
		// I have no idea why not including this just skips initGoals(), but I really don't care at this point,
		// and I'm just glad this works right now.
		super.initGoals();
	}

	@Override
	public boolean tryAttack(Entity target) {
		boolean bl = super.tryAttack(target);

		if (bl && this.getMainHandStack().isEmpty() && target instanceof LivingEntity) {
			// use this to scale the effect's duration based on the local difficulty
			int localDiff = (int) this.getWorld().getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
			// inflict temporary nausea
			((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200 * localDiff), this);
		}

		return bl;
	}
}
