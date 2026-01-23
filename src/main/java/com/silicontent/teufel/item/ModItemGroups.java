package com.silicontent.teufel.item;

import com.silicontent.teufel.Teufel;
import com.silicontent.teufel.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
	public static final ItemGroup BLOCKS_MATERIALS_GROUP = Registry.register(Registries.ITEM_GROUP,
			new Identifier(Teufel.MOD_ID, "blocks_materials"),
			FabricItemGroup.builder()
					.displayName(Text.translatable("itemgroup.teufel.blocks_materials"))
					.icon(() -> new ItemStack(ModItems.ULTIM_EYE))
					.entries(((displayContext, entries) -> {

						entries.add(ModBlocks.LIVING_LOG);
						entries.add(ModBlocks.LIVING_WOOD);
						entries.add(ModBlocks.STRIPPED_LIVING_LOG);
						entries.add(ModBlocks.STRIPPED_LIVING_WOOD);

						entries.add(ModBlocks.LIVING_PLANKS);
						entries.add(ModBlocks.LIVING_STAIRS);
						entries.add(ModBlocks.LIVING_SLAB);
						entries.add(ModBlocks.LIVING_FENCE);
						entries.add(ModBlocks.LIVING_FENCE_GATE);
						entries.add(ModBlocks.LIVING_DOOR);
						entries.add(ModBlocks.LIVING_TRAPDOOR);
						entries.add(ModBlocks.LIVING_PRESSURE_PLATE);
						entries.add(ModBlocks.LIVING_BUTTON);

						entries.add(ModBlocks.HELLFIRE_ORE);
						entries.add(ModBlocks.HELLFIRE_BLOCK);
						entries.add(ModBlocks.TERMINITE_BLOCK);
						entries.add(ModBlocks.SCULKEN_BLOCK);
						entries.add(ModBlocks.DEMONITE_BLOCK);

						entries.add(ModItems.REINFORCED_STICK);
						entries.add(ModItems.WITHERED_BONE);
						entries.add(ModItems.OBSIDIAN_FRAGMENT);
						entries.add(ModItems.ULTIM_EYE);

						entries.add(ModItems.LIFE_ESSENCE);
						entries.add(ModItems.DEATH_ESSENCE);
						entries.add(ModItems.FIRE_ESSENCE);
						entries.add(ModItems.WATER_ESSENCE);
						entries.add(ModItems.PEACE_ESSENCE);
						entries.add(ModItems.PAIN_ESSENCE);

						entries.add(ModItems.HELLFIRE_CHUNK);
						entries.add(ModItems.METEOR_INGOT);
						entries.add(ModItems.HELLFIRE_INGOT);
						entries.add(ModItems.TERMINITE_INGOT);
						entries.add(ModItems.SCULKEN_INGOT);
						entries.add(ModItems.DEMONITE_INGOT);

					})).build());

	public static final ItemGroup WEAPONS_GROUP = Registry.register(Registries.ITEM_GROUP,
			new Identifier(Teufel.MOD_ID, "weapons"),
			FabricItemGroup.builder()
					.displayName(Text.translatable("itemgroup.teufel.weapons"))
					.icon(() -> new ItemStack(ModItems.FLINT_DAGGER))
					.entries(((displayContext, entries) -> {

						entries.add(ModItems.DIRT_BALL);
						entries.add(ModItems.FLINT_DAGGER);
						entries.add(ModItems.COPPER_DAGGER);
						entries.add(ModItems.METEOR_SHARD);
						entries.add(ModItems.FROSTBITE);
						entries.add(ModItems.OBSIDIAN_SWORD);
						entries.add(ModItems.PIGLIN_WARAXE);
						entries.add(ModItems.INFERNO);
						entries.add(ModItems.DEITY_BLADE);
						entries.add(ModItems.ENDER_SWORD);
						entries.add(ModItems.TERMINUS);
						entries.add(ModItems.DEEPSLASH);
						entries.add(ModItems.TEUFEL);
						entries.add(ModItems.ULTIMATE_HOE);
						entries.add(ModItems.COPPER_INJECTION);

					})).build());

	public static void registerItemGroups() {
		Teufel.LOGGER.info("Registering item groups for " + Teufel.MOD_ID);
	}
}
