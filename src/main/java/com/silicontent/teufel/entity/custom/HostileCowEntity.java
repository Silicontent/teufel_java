package com.silicontent.teufel.entity.custom;

import com.silicontent.teufel.entity.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HostileCowEntity extends CowEntity {
	public HostileCowEntity(EntityType<? extends CowEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initGoals() {
		// lower priority number = higher priority
		// usually want swim goal at priority 0 so mob doesn't drown
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, false));
		this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
		this.goalSelector.add(4, new LookAroundGoal(this));
	}

	public static DefaultAttributeContainer.Builder createCowAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24);
	}

	@Nullable
	@Override
	public CowEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return ModEntities.HOSTILE_COW.create(serverWorld);
	}

	// BEHAVIORS ==================================================================================
	@Override
	public boolean tryAttack(Entity target) {
		return super.tryAttack(target);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return super.isBreedingItem(stack);
	}
}
