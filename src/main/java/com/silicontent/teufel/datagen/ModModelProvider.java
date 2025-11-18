package com.silicontent.teufel.datagen;

import com.silicontent.teufel.block.ModBlocks;
import com.silicontent.teufel.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		// simple blocks with same texture all over
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.HELLFIRE_ORE);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.HELLFIRE_BLOCK);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TERMINITE_BLOCK);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SCULKEN_BLOCK);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TEUFEL_BLOCK);

		// living plank blocks
		BlockStateModelGenerator.BlockTexturePool livingPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.LIVING_PLANKS);

		livingPool.stairs(ModBlocks.LIVING_STAIRS);
		livingPool.slab(ModBlocks.LIVING_SLAB);
		livingPool.button(ModBlocks.LIVING_BUTTON);
		livingPool.pressurePlate(ModBlocks.LIVING_PRESSURE_PLATE);
		livingPool.fence(ModBlocks.LIVING_FENCE);
		livingPool.fenceGate(ModBlocks.LIVING_FENCE_GATE);

		blockStateModelGenerator.registerDoor(ModBlocks.LIVING_DOOR);
		blockStateModelGenerator.registerTrapdoor(ModBlocks.LIVING_TRAPDOOR);

		blockStateModelGenerator.registerLog(ModBlocks.LIVING_LOG).log(ModBlocks.LIVING_LOG).wood(ModBlocks.LIVING_WOOD);
		blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_LIVING_LOG).log(ModBlocks.STRIPPED_LIVING_LOG).wood(ModBlocks.STRIPPED_LIVING_WOOD);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		// simple items
		itemModelGenerator.register(ModItems.OBSIDIAN_FRAGMENT, Models.GENERATED);
		itemModelGenerator.register(ModItems.ULTIM_EYE, Models.GENERATED);

		itemModelGenerator.register(ModItems.LIFE_ESSENCE, Models.GENERATED);
		itemModelGenerator.register(ModItems.DEATH_ESSENCE, Models.GENERATED);
		itemModelGenerator.register(ModItems.FIRE_ESSENCE, Models.GENERATED);
		itemModelGenerator.register(ModItems.FROST_ESSENCE, Models.GENERATED);
		itemModelGenerator.register(ModItems.PEACE_ESSENCE, Models.GENERATED);
		itemModelGenerator.register(ModItems.PAIN_ESSENCE, Models.GENERATED);

		itemModelGenerator.register(ModItems.HELLFIRE_CHUNK, Models.GENERATED);
		itemModelGenerator.register(ModItems.METEOR_INGOT, Models.GENERATED);
		itemModelGenerator.register(ModItems.HELLFIRE_INGOT, Models.GENERATED);
		itemModelGenerator.register(ModItems.TERMINITE_INGOT, Models.GENERATED);
		itemModelGenerator.register(ModItems.SCULKEN_INGOT, Models.GENERATED);
		itemModelGenerator.register(ModItems.TEUFEL_INGOT, Models.GENERATED);

		itemModelGenerator.register(ModItems.DIRT_BALL, Models.GENERATED);

		itemModelGenerator.register(ModItems.TOTEM, Models.GENERATED);
		itemModelGenerator.register(ModItems.SPEED_TOTEM, Models.GENERATED);
		itemModelGenerator.register(ModItems.STRENGTH_TOTEM, Models.GENERATED);
		itemModelGenerator.register(ModItems.JUMP_TOTEM, Models.GENERATED);
		itemModelGenerator.register(ModItems.SAFETY_TOTEM, Models.GENERATED);
		itemModelGenerator.register(ModItems.MOVE_TOTEM, Models.GENERATED);
		itemModelGenerator.register(ModItems.WATER_TOTEM, Models.GENERATED);
		itemModelGenerator.register(ModItems.DEATH_TOTEM, Models.GENERATED);

		// handheld items
		itemModelGenerator.register(ModItems.REINFORCED_STICK, Models.HANDHELD);
		itemModelGenerator.register(ModItems.WITHERED_BONE, Models.HANDHELD);

		itemModelGenerator.register(ModItems.FLINT_DAGGER, Models.HANDHELD);
		itemModelGenerator.register(ModItems.COPPER_DAGGER, Models.HANDHELD);
		itemModelGenerator.register(ModItems.METEOR_SHARD, Models.HANDHELD);
		itemModelGenerator.register(ModItems.FROSTBITE, Models.HANDHELD);
		itemModelGenerator.register(ModItems.OBSIDIAN_SWORD, Models.HANDHELD);
		itemModelGenerator.register(ModItems.PIGLIN_WARAXE, Models.HANDHELD);
		itemModelGenerator.register(ModItems.INFERNO, Models.HANDHELD);
		itemModelGenerator.register(ModItems.DEITY_BLADE, Models.HANDHELD);
		itemModelGenerator.register(ModItems.ENDER_SWORD, Models.HANDHELD);
		itemModelGenerator.register(ModItems.TERMINUS, Models.HANDHELD);
		itemModelGenerator.register(ModItems.DEEPSLASH, Models.HANDHELD);
		itemModelGenerator.register(ModItems.TEUFEL, Models.HANDHELD);
		itemModelGenerator.register(ModItems.ULTIMATE_HOE, Models.HANDHELD);
		itemModelGenerator.register(ModItems.COPPER_INJECTION, Models.HANDHELD);
	}
}
