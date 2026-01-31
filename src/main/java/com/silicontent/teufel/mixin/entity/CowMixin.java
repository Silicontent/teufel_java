package com.silicontent.teufel.mixin.entity;

import com.silicontent.teufel.item.ModItems;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public abstract class CowMixin extends AnimalEntity {
	protected CowMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * @author Silicontent (mod_id: teufel)
	 * @reason Changes cow attributes to allow for hostile behavior.
	 */
	@Overwrite
	public static DefaultAttributeContainer.Builder createCowAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.1)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24);
	}

	/**
	 * @author Silicontent (mod_id: teufel)
	 * @reason Modifies cow behavior to make it hostile towards the player
	 */
	@Overwrite
	public void initGoals() {
		// lower priority number = higher priority
		// usually want swim goal at priority 0 so mob doesn't drown
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
		this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
		this.goalSelector.add(4, new LookAroundGoal(this));
		this.goalSelector.add(4, new FollowParentGoal(this, 1.25));
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

	// BEHAVIOR SWITCHING =========================================================================
	@Unique
	public void resetGoals() {
		this.goalSelector.clear(goal -> true);
		this.targetSelector.clear(goal -> true);
	}

	@Unique
	public void initPassiveGoals() {
		resetGoals();

		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
		this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems(Items.WHEAT), false));
		this.goalSelector.add(4, new FollowParentGoal(this, 1.25));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(7, new LookAroundGoal(this));
	}

	@Inject(method = "interactMob", at = @At("HEAD"))
	public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!this.getWorld().isClient && itemStack.isOf(ModItems.PEACE_ESSENCE)) {
			initPassiveGoals();
			// consumes an essence upon use
			if (!player.isCreative()) {
				itemStack.decrement(1);
			}
		}
	}
}
