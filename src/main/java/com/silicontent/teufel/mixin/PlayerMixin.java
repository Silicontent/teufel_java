package com.silicontent.teufel.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends Entity {
    @Shadow public abstract boolean damage(DamageSource source, float amount);

    public PlayerMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        /*
        TODO
            Find a better way to do this so that potion effects and armor can
            negate or otherwise lessen these
        */

        // instantly begin drowning upon going underwater
        if (this.isSubmergedInWater()) {
            this.setAir(-20);
            this.damage(this.getDamageSources().drown(), 2.0f);
        }

        // instantly kill the player upon touching lava
        if (this.isInLava()) {
            this.kill();
        }
    }
}
