package com.silicontent.teufel.mixin.entity.hostile;

import com.silicontent.teufel.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SilverfishEntity.class)
public abstract class SilverfishMixin extends HostileEntity {
	protected SilverfishMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * @author Silicontent (mod_id: teufel)
	 * @reason Buff entity attributes.
	 */
	@Overwrite
	public static DefaultAttributeContainer.Builder createSilverfishAttributes() {
		return HostileEntity.createHostileAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 64.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0);
	}

	@Override
	public boolean handleAttack(Entity attacker) {
		// instantly kill the silverfish if an emerald sword is used to attack it
		PlayerEntity player = (PlayerEntity) attacker;
		if (player != null && player.getMainHandStack().isOf(ModItems.EMERALD_SWORD)) {
			this.damage(this.getDamageSources().genericKill(), Float.MAX_VALUE);
			player.getMainHandStack().decrement(1);
		}
		return super.handleAttack(attacker);
	}
}
