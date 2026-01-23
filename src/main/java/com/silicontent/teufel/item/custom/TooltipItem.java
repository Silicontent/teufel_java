package com.silicontent.teufel.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipItem extends Item {
	public final String tooltipKey;

	public TooltipItem(Settings settings, String key) {
		super(settings);
		this.tooltipKey = key;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable(this.tooltipKey));
	}
}
