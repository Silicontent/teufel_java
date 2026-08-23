package com.silicontent.teufel.mixin.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public abstract class TooltipMixin {
	@Inject(method = "appendTooltip", at = @At("HEAD"))
	private void addCustomTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
		// if no custom tooltip exists, don't append anything; else, start appending
		if (!Text.translatable("tooltip.teufel." + this).getString().equals("tooltip.teufel." + this)) {
			// get all lines of the tooltip from the lang file (if there are multiple lines)
			String[] lines = Text.translatable("tooltip.teufel." + this).getString().split("\n");
			for (String line : lines) {
				// add tooltip line (making the text gray by default)
				tooltip.add(Text.of("§7" + line));
			}
		}
	}
}
