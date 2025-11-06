package com.silicontent.teufel.datagen;

import com.silicontent.teufel.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        // tool requirements
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.HELLFIRE_ORE)
                .add(ModBlocks.HELLFIRE_BLOCK)
                .add(ModBlocks.TERMINITE_BLOCK)
                .add(ModBlocks.SCULKEN_BLOCK)
                .add(ModBlocks.TEUFEL_BLOCK);

        // tool tiers
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.HELLFIRE_ORE)
                .add(ModBlocks.HELLFIRE_BLOCK)
                .add(ModBlocks.TERMINITE_BLOCK);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.SCULKEN_BLOCK)
                .add(ModBlocks.TEUFEL_BLOCK);

        // living planks and variants
        getOrCreateTagBuilder(BlockTags.PLANKS)
                .add(ModBlocks.LIVING_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.LIVING_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.LIVING_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.LIVING_BUTTON);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.LIVING_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.LIVING_FENCE);
        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.LIVING_DOOR);
        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.LIVING_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.FENCES)
                .add(ModBlocks.LIVING_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(ModBlocks.LIVING_FENCE_GATE);

        // burnable logs tag (used as fuel)
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.LIVING_LOG)
                .add(ModBlocks.LIVING_WOOD)
                .add(ModBlocks.STRIPPED_LIVING_LOG)
                .add(ModBlocks.STRIPPED_LIVING_WOOD);
    }
}
