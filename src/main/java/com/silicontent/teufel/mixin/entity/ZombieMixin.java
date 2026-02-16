package com.silicontent.teufel.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ZombieEntity.class)
public abstract class ZombieMixin extends HostileEntity {
	protected ZombieMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * @author Silicontent (mod_id: teufel)
	 * @reason Change zombie to fit better in progression.
	 */
	@Overwrite
	public static DefaultAttributeContainer.Builder createZombieAttributes() {
		return HostileEntity.createHostileAttributes()
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26F)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0)
				.add(EntityAttributes.GENERIC_ARMOR, 2.0)
				.add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
	}

	/**
	 * @author Silicontent (mod_id: teufel)
	 * @reason Zombies no longer burn in daylight.
	 */
//	@Overwrite
//	public boolean burnsInDaylight() {
//		return false;
//	}
}
