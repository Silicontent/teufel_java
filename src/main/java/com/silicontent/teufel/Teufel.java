package com.silicontent.teufel;

import com.silicontent.teufel.block.ModBlocks;
import com.silicontent.teufel.entity.ModEntities;
import com.silicontent.teufel.item.ModItemGroups;
import com.silicontent.teufel.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Teufel implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "teufel";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		ModEntities.registerModEntities();

		// enable log stripping on living logs (can be done in its own class)
		StrippableBlockRegistry.register(ModBlocks.LIVING_LOG, ModBlocks.STRIPPED_LIVING_LOG);
		StrippableBlockRegistry.register(ModBlocks.LIVING_WOOD, ModBlocks.STRIPPED_LIVING_WOOD);

		// set flammability of blocks (burn and spread from FireBlocks class)
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIVING_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIVING_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_LIVING_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_LIVING_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIVING_PLANKS, 5, 20);
	}
}