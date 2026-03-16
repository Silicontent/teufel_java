package com.silicontent.teufel.mixin.entity.other;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EggEntity.class)
public abstract class EggMixin extends ThrownItemEntity {
	public EggMixin(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) {
		super(entityType, livingEntity, world);
	}

	@Override
	public void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		// deal damage to the entity
		entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 5.0F);

		// moving this check here from onCollision() prevents entities from spawning only if the egg hits a block
		if (!this.getWorld().isClient) {
			// randomly spawn one entity
			if (this.random.nextInt(8) == 0) {
				int i = 1;
				// more rarely, spawn four entities
				if (this.random.nextInt(64) == 0) {
					i = 4;
				}

				// spawn the needed entities
				for (int j = 0; j < i; j++) {
					ZombieEntity zombie = EntityType.ZOMBIE.create(this.getWorld());
					if (zombie != null) {
						zombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
						this.getWorld().spawnEntity(zombie);
					}
				}
			}
		}
	}

	@Override
	public void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.getWorld().isClient) {
			this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
			this.discard();
		}
	}
}
