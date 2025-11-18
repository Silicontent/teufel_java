package com.silicontent.teufel.block;

import com.silicontent.teufel.Teufel;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
	// hellfire blocks
	public static final Block HELLFIRE_ORE = registerBlock("hellfire_ore",
			new ExperienceDroppingBlock(AbstractBlock.Settings.copy(Blocks.NETHERRACK).strength(3.0f, 3.0f), UniformIntProvider.create(2, 5)));
	public static final Block HELLFIRE_BLOCK = registerBlock("hellfire_block",
			new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE)));
	public static final Block TERMINITE_BLOCK = registerBlock("terminite_block",
			new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE).strength(5.0f)));
	public static final Block SCULKEN_BLOCK = registerBlock("sculken_block",
			new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE).strength(5.0f)));
	public static final Block TEUFEL_BLOCK = registerBlock("teufel_block",
			new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE).strength(6.6f)));

	// living wood & planks
	public static final Block LIVING_LOG = registerBlock("living_log",
			new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)));
	public static final Block LIVING_WOOD = registerBlock("living_wood",
			new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD)));
	public static final Block STRIPPED_LIVING_LOG = registerBlock("stripped_living_log",
			new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG)));
	public static final Block STRIPPED_LIVING_WOOD = registerBlock("stripped_living_wood",
			new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD)));

	public static final Block LIVING_PLANKS = registerBlock("living_planks",
			new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));
	public static final Block LIVING_STAIRS = registerBlock("living_stairs",
			new StairsBlock(ModBlocks.LIVING_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_STAIRS)));
	public static final Block LIVING_SLAB = registerBlock("living_slab",
			new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_SLAB)));
	public static final Block LIVING_BUTTON = registerBlock("living_button",
			new ButtonBlock(AbstractBlock.Settings.copy(Blocks.OAK_BUTTON), BlockSetType.OAK, 30, true));
	public static final Block LIVING_PRESSURE_PLATE = registerBlock("living_pressure_plate",
			new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetType.OAK));
	public static final Block LIVING_FENCE = registerBlock("living_fence",
			new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE)));
	public static final Block LIVING_FENCE_GATE = registerBlock("living_fence_gate",
			new FenceGateBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE), WoodType.OAK));
	public static final Block LIVING_DOOR = registerBlock("living_door",
			new DoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_DOOR), BlockSetType.OAK));
	public static final Block LIVING_TRAPDOOR = registerBlock("living_trapdoor",
			new TrapdoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_DOOR), BlockSetType.OAK));


	private static Block registerBlock(String name, Block block) {
		// register block item
		registerBlockItem(name, block);
		// register actual block
		return Registry.register(Registries.BLOCK, new Identifier(Teufel.MOD_ID, name), block);
	}

	private static Item registerBlockItem(String name, Block block) {
		// register the item that represents the block in the inventory
		return Registry.register(Registries.ITEM, new Identifier(Teufel.MOD_ID, name),
				new BlockItem(block, new Item.Settings()));
	}

	public static void registerModBlocks() {
		Teufel.LOGGER.info("Registering mod blocks for " + Teufel.MOD_ID);
	}
}
