package com.silicontent.teufel.mixin.entity.boss;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WardenEntity.class)
public class WardenMixin extends HostileEntity {
	@Unique
	private final ServerBossBar bossBar = (ServerBossBar) new ServerBossBar(this.getDisplayName(), BossBar.Color.BLUE, BossBar.Style.PROGRESS).setDarkenSky(true);

	protected WardenMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "mobTick", at = @At("TAIL"))
	public void mobTick(CallbackInfo ci) {
		this.bossBar.setVisible(this.getHealth() < 0);
		this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
	}
}
