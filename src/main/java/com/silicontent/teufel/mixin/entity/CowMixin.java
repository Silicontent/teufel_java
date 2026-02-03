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

import java.util.ArrayList;

@Mixin(CowEntity.class)
public abstract class CowMixin extends AnimalEntity {
	@Unique
	ArrayList<PrioritizedGoal> commonGoals;
	@Unique
	ArrayList<PrioritizedGoal> hostileGoals;
	@Unique
	ArrayList<PrioritizedGoal> passiveGoals;

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
		// create goal lists
		this.commonGoals = new ArrayList<>();
		this.hostileGoals = new ArrayList<>();
		this.passiveGoals = new ArrayList<>();

		this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

		// populate goal lists
		this.commonGoals.add(new PrioritizedGoal(0, new SwimGoal(this)));
		this.commonGoals.add(new PrioritizedGoal(2, new WanderAroundFarGoal(this, 1.0)));
		this.commonGoals.add(new PrioritizedGoal(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f)));
		this.commonGoals.add(new PrioritizedGoal(4, new LookAroundGoal(this)));
		this.commonGoals.add(new PrioritizedGoal(4, new FollowParentGoal(this, 1.25)));
		this.hostileGoals.add(new PrioritizedGoal(1, new MeleeAttackGoal(this, 1.0, false)));
		this.passiveGoals.add(new PrioritizedGoal(1, new EscapeDangerGoal(this, 2.0)));
		this.passiveGoals.add(new PrioritizedGoal(2, new AnimalMateGoal(this, 1.0)));
		this.passiveGoals.add(new PrioritizedGoal(3, new TemptGoal(this, 1.25, Ingredient.ofItems(Items.WHEAT), false)));

		// add goals to selector
		for (PrioritizedGoal g : this.commonGoals) {
			this.addPrioritized(g);
		}
		this.updateGoalSelector(hostileGoals);
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
	public void addPrioritized(PrioritizedGoal g) {
		this.goalSelector.add(g.getPriority(), g.getGoal());
	}

	@Unique
	public void removePrioritized(PrioritizedGoal g) {
		this.goalSelector.remove(g.getGoal());
	}

	@Unique
	public void updateGoalSelector(ArrayList<PrioritizedGoal> goals) {
		// remove undesired goals
		for (PrioritizedGoal g : this.goalSelector.getGoals()) {
			if (!(goals.contains(g) || this.commonGoals.contains(g))) {
				this.removePrioritized(g);
			}
		}
		// add goals from list
		for (PrioritizedGoal g : goals) {
			this.addPrioritized(g);
		}
	}

	@Inject(method = "interactMob", at = @At("HEAD"))
	public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!this.getWorld().isClient) {
			if (itemStack.isOf(ModItems.PEACE_ESSENCE)) {
				updateGoalSelector(this.passiveGoals);
				// consumes an essence upon use
				if (!player.isCreative()) {
					itemStack.decrement(1);
				}
			}
			else if (itemStack.isOf(ModItems.PAIN_ESSENCE)) {
				updateGoalSelector(this.hostileGoals);
				// consumes an essence upon use
				if (!player.isCreative()) {
					itemStack.decrement(1);
				}
			}
		}
	}
}
