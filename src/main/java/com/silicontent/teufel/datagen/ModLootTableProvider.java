package com.silicontent.teufel.datagen;

import com.silicontent.teufel.block.ModBlocks;
import com.silicontent.teufel.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
	public ModLootTableProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	public LootTable.Builder manyOreDrops(Block drop, Item item, float min, float max) {
		// create an ore loot table with variable amount of items
		return BlockLootTableGenerator.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop,
				((LeafEntry.Builder<?>)
						ItemEntry.builder(item)
								.apply(SetCountLootFunction
										.builder(UniformLootNumberProvider.create(min, max))))
						.apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
	}

	@Override
	public void generate() {
		// self-dropping blocks
		addDrop(ModBlocks.HELLFIRE_BLOCK);
		addDrop(ModBlocks.TERMINITE_BLOCK);
		addDrop(ModBlocks.SCULKEN_BLOCK);
		addDrop(ModBlocks.TEUFEL_BLOCK);

		addDrop(ModBlocks.LIVING_LOG);
		addDrop(ModBlocks.LIVING_WOOD);
		addDrop(ModBlocks.STRIPPED_LIVING_LOG);
		addDrop(ModBlocks.STRIPPED_LIVING_WOOD);
		addDrop(ModBlocks.LIVING_PLANKS);
		addDrop(ModBlocks.LIVING_SLAB, slabDrops(ModBlocks.LIVING_SLAB));
		addDrop(ModBlocks.LIVING_STAIRS);
		addDrop(ModBlocks.LIVING_BUTTON);
		addDrop(ModBlocks.LIVING_PRESSURE_PLATE);
		addDrop(ModBlocks.LIVING_FENCE);
		addDrop(ModBlocks.LIVING_FENCE_GATE);
		addDrop(ModBlocks.LIVING_DOOR, doorDrops(ModBlocks.LIVING_DOOR));
		addDrop(ModBlocks.LIVING_TRAPDOOR);

		// ore blocks
		addDrop(ModBlocks.HELLFIRE_ORE, manyOreDrops(ModBlocks.HELLFIRE_ORE, ModItems.HELLFIRE_CHUNK, 2.0f, 5.0f));
	}
}
