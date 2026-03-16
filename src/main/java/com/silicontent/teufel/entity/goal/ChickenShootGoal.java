package com.silicontent.teufel.entity.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.world.WorldEvents;

import java.util.EnumSet;

public class ChickenShootGoal extends Goal {
	/*
	All of this logic was shamelessly stolen from the Blaze's
	ShootFireballGoal. It has been tweaked to add comments and
	chicken-specific mechanics.
	*/

	private final ChickenEntity chicken;
	private int eggsFired;
	private int eggCooldown;
	private int targetNotVisibleTicks;
	// number of eggs shot out at a time (idk I thought the name would be funny)
	private final int magSize;

	public ChickenShootGoal(ChickenEntity chicken) {
		this.chicken = chicken;
		this.magSize = 10;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean canStart() {
		LivingEntity livingEntity = this.chicken.getTarget();
		return livingEntity != null && livingEntity.isAlive() && this.chicken.canTarget(livingEntity);
	}

	@Override
	public void start() {
		this.eggsFired = 0;
	}

	@Override
	public void stop() {
		this.targetNotVisibleTicks = 0;
	}

	@Override
	public boolean shouldRunEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		this.eggCooldown--;
		// get nearest target, if any
		LivingEntity target = this.chicken.getTarget();

		if (target != null) {
			// see if the target can be seen
			boolean inSight = this.chicken.getVisibilityCache().canSee(target);
			if (inSight) {
				this.targetNotVisibleTicks = 0;
			} else {
				this.targetNotVisibleTicks++;
			}

			// get the distance to the target
			double d = this.chicken.squaredDistanceTo(target);
			// if target is in melee range
			if (d < 4.0) {
				// do nothing extra if the target can't be seen
				if (!inSight) {
					return;
				}

				// reset shooting cooldown and attempt melee attack
				if (this.eggCooldown <= 0) {
					this.eggCooldown = 20;
					this.chicken.tryAttack(target);
				}

				// move chicken closer to the target
				this.chicken.getMoveControl().moveTo(target.getX(), target.getY(), target.getZ(), 1.0);
			}
			// if target is in firing range
			else if (d < this.getFollowRange() * this.getFollowRange() && inSight) {
				// get position to fire at
				double e = target.getX() - this.chicken.getX();
				double f = target.getBodyY(0.5) - this.chicken.getBodyY(0.5);
				double g = target.getZ() - this.chicken.getZ();

				// begin firing at the target
				if (this.eggCooldown <= 0) {
					this.eggsFired++;
					// set cooldown between shots
					if (this.eggsFired <= this.magSize) {
						this.eggCooldown = 1;
					} else {
						// reset firing
						this.eggCooldown = 100;
						this.eggsFired = 0;
					}

					// spawn egg entities to be fired at the target
					if (this.eggsFired > 1) {
						// play the egg shooting (aka dispenser launching) sound effect
						if (!this.chicken.isSilent()) {
							this.chicken.getWorld().syncWorldEvent(null, WorldEvents.DISPENSER_LAUNCHES_PROJECTILE, this.chicken.getBlockPos(), 0);
						}

						// create new egg entity
						EggEntity egg = new EggEntity(this.chicken.getWorld(), this.chicken);
						// set position and velocity
						egg.setPosition(egg.getX(), this.chicken.getBodyY(0.5) + 0.5, egg.getZ());
						egg.setVelocity(e, f, g, 1.25f, 15.0f);
						// spawn the egg into the world
						this.chicken.getWorld().spawnEntity(egg);
					}
				}

				// look at the target
				this.chicken.getLookControl().lookAt(target, 10.0F, 10.0F);
			}
			else if (this.targetNotVisibleTicks < 5) {
				// move closer to the target
				this.chicken.getMoveControl().moveTo(target.getX(), target.getY(), target.getZ(), 1.0);
			}

			super.tick();
		}
	}

	private double getFollowRange() {
		// get follow range from the chicken
		return this.chicken.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
	}
}
