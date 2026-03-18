package com.silicontent.teufel.mixin.entity.passive;

import com.silicontent.teufel.entity.goal.ChickenShootGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChickenEntity.class)
public abstract class ChickenMixin extends HostileAnimalMixin {
	/*
	NOTE: The chicken's hostile behavior is entirely managed by the ChickenShootGoal
	 located at com/silicontent/teufel/entity/goal/ChickenShootGoal
	*/

	@Shadow
	@Final
	private static Ingredient BREEDING_INGREDIENT;

	protected ChickenMixin(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * @author Silicontent (mod_id: teufel)
	 * @reason Changes entity attributes to fit with new hostile behavior.
	 */
	@Overwrite
	public static DefaultAttributeContainer.Builder createChickenAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
	}

	@Override
	public void populateGoals() {
		// populate goal lists
		this.getCommonGoals().add(new PrioritizedGoal(0, new SwimGoal(this)));
		this.getCommonGoals().add(new PrioritizedGoal(4, new FollowParentGoal((AnimalEntity) (Object) this, 1.1)));
		this.getCommonGoals().add(new PrioritizedGoal(5, new WanderAroundFarGoal(this, 1.0)));
		this.getCommonGoals().add(new PrioritizedGoal(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F)));
		this.getCommonGoals().add(new PrioritizedGoal(7, new LookAroundGoal(this)));
		this.getHostileGoals().add(new PrioritizedGoal(1, new ChickenShootGoal((ChickenEntity) (Object) this)));
		this.getPassiveGoals().add(new PrioritizedGoal(1, new EscapeDangerGoal(this, 1.4)));
		this.getPassiveGoals().add(new PrioritizedGoal(2, new AnimalMateGoal((AnimalEntity) (Object) this, 1.0)));
		this.getPassiveGoals().add(new PrioritizedGoal(3, new TemptGoal(this, 1.0, BREEDING_INGREDIENT, false)));

		// adds player as target
		this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	public void initGoals() {
		super.initGoals();
	}
}
