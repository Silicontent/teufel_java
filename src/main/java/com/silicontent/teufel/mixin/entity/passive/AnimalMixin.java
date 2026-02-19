package com.silicontent.teufel.mixin.entity.passive;

import com.silicontent.teufel.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Objects;

@Mixin(AnimalEntity.class)
public abstract class AnimalMixin extends PassiveEntity {
	@Unique
	// AI goals that the entity has no matter its current state
	private ArrayList<PrioritizedGoal> commonGoals;
	@Unique
	// AI goals the entity uses when hostile
	private ArrayList<PrioritizedGoal> hostileGoals;
	@Unique
	// AI goals the entity uses when passive
	private ArrayList<PrioritizedGoal> passiveGoals;

	@Unique
	// determines if the entity is currently passive or not
	private boolean passive;

	protected AnimalMixin(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
	}

	@Unique
	// overridden by other entities to add their own AI goals
	public void populateGoals() {
		// adds player as target for entity, target must be seen by default
		this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	public void initGoals() {
		// create goal lists
		this.setCommonGoals(new ArrayList<>());
		this.setHostileGoals(new ArrayList<>());
		this.setPassiveGoals(new ArrayList<>());

		// populate goal lists
		populateGoals();

		// add goals to selector
		for (PrioritizedGoal g : this.commonGoals) {
			this.addPrioritized(g);
		}
		this.updateGoalSelector(hostileGoals);
	}

	// PERSISTENT BEHAVIOR STATE ==================================================================
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("Passive", this.getPassive());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.passive = nbt.getBoolean("Passive");

		// turn entity passive if previous state was passive
		if (this.getPassive()) {
			this.updateGoalSelector(this.getPassiveGoals());
		}
	}

	// BEHAVIOR SWITCHING =========================================================================
	@Unique
	private void addPrioritized(PrioritizedGoal g) {
		this.goalSelector.add(g.getPriority(), g.getGoal());
	}

	@Unique
	private void removePrioritized(PrioritizedGoal g) {
		Objects.requireNonNull(this.getServer()).execute(() -> this.goalSelector.remove(g.getGoal()));
	}

	@Unique
	private void updateGoalSelector(ArrayList<PrioritizedGoal> goals) {
		// remove undesired goals
		this.goalSelector.getGoals().stream().filter(g -> !(goals.contains(g) || this.getCommonGoals().contains(g))).forEach(this::removePrioritized);
		// add goals from list
		for (PrioritizedGoal g : goals) {
			this.addPrioritized(g);
		}
	}

	@Unique
	private void useGoalItem(ArrayList<PrioritizedGoal> goals, PlayerEntity player, ItemStack stack) {
		updateGoalSelector(goals);
		// consume an item if not in Creative Mode
		if (!player.isCreative()) {
			stack.decrement(1);
		}
		// flip the passive flag
		this.setPassive(!this.getPassive());
	}

	@Inject(method = "interactMob", at = @At("HEAD"))
	public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!this.getWorld().isClient) {
			// turn entity passive
			if (itemStack.isOf(ModItems.PEACE_ESSENCE) && !this.getPassive()) {
				useGoalItem(this.getPassiveGoals(), player, itemStack);
			}
			// turn entity hostile
			else if (itemStack.isOf(ModItems.PAIN_ESSENCE) && this.getPassive()) {
				useGoalItem(this.getHostileGoals(), player, itemStack);
			}
		}
	}

	// GETTERS ====================================================================================
	@Unique
	public ArrayList<PrioritizedGoal> getCommonGoals() {
		return this.commonGoals;
	}

	@Unique
	public ArrayList<PrioritizedGoal> getPassiveGoals() {
		return this.passiveGoals;
	}

	@Unique
	public ArrayList<PrioritizedGoal> getHostileGoals() {
		return this.hostileGoals;
	}

	@Unique
	public boolean getPassive() {
		return this.passive;
	}

	// SETTERS ====================================================================================
	@Unique
	public void setCommonGoals(ArrayList<PrioritizedGoal> goals) {
		this.commonGoals = goals;
	}

	@Unique
	public void setPassiveGoals(ArrayList<PrioritizedGoal> goals) {
		this.passiveGoals = goals;
	}

	@Unique
	public void setHostileGoals(ArrayList<PrioritizedGoal> goals) {
		this.hostileGoals = goals;
	}

	@Unique
	public void setPassive(boolean p) {
		this.passive = p;
	}
}
