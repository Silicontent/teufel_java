package com.silicontent.teufel.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PigEntity.class)
public abstract class PigMixin extends AnimalMixin {
	@Shadow
	private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.CARROT, Items.POTATO, Items.BEETROOT);

	protected PigMixin(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * @author Silicontent (mod_id: teufel)
	 * @reason Changes entity attributes to fit with new hostile behavior.
	 */
	@Overwrite
	public static DefaultAttributeContainer.Builder createPigAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 14.0);
	}

	@Override
	public void populateGoals() {
		// add goals
		this.getCommonGoals().add(new PrioritizedGoal(0, new SwimGoal(this)));
		this.getCommonGoals().add(new PrioritizedGoal(5, new FollowParentGoal((AnimalEntity) (Object) this, 1.1)));
		this.getCommonGoals().add(new PrioritizedGoal(6, new WanderAroundFarGoal(this, 1.0)));
		this.getCommonGoals().add(new PrioritizedGoal(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F)));
		this.getCommonGoals().add(new PrioritizedGoal(8, new LookAroundGoal(this)));
		this.getHostileGoals().add(new PrioritizedGoal(1, new MeleeAttackGoal(this, 1.0, false)));
		this.getPassiveGoals().add(new PrioritizedGoal(1, new EscapeDangerGoal(this, 1.25)));
		this.getPassiveGoals().add(new PrioritizedGoal(3, new AnimalMateGoal((AnimalEntity) (Object) this, 1.0)));
		this.getPassiveGoals().add(new PrioritizedGoal(4, new TemptGoal(this, 1.2, Ingredient.ofItems(Items.CARROT_ON_A_STICK), false)));
		this.getPassiveGoals().add(new PrioritizedGoal(4, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false)));

		super.populateGoals();
	}

	@Override
	public void initGoals() {
		super.initGoals();
	}
}
