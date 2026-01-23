package com.silicontent.teufel.item.weapons;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CopperDaggerItem extends SwordItem {
	public CopperDaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("tooltip.teufel.copper_dagger"));
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		// TODO: prevent the player from also taking damage? apply a short Fire Resistance?
		if (attacker.getRandom().nextFloat() < 0.05) {
			// get reference to the world so the lightning can be spawned in
			World world = attacker.getWorld();
			// create and spawn a lightning bolt at the target's position
			LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
			lightning.setPos(target.getX(), target.getY(), target.getZ());
			world.spawnEntity(lightning);
		}

		return super.postHit(stack, target, attacker);
	}
}
