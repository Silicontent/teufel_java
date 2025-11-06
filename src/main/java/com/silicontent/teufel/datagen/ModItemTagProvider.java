package com.silicontent.teufel.datagen;

import com.silicontent.teufel.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        // planks tag
        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(ModBlocks.LIVING_PLANKS.asItem());

        // burnable logs tag (used as fuel)
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.LIVING_LOG.asItem())
                .add(ModBlocks.LIVING_WOOD.asItem())
                .add(ModBlocks.STRIPPED_LIVING_LOG.asItem())
                .add(ModBlocks.STRIPPED_LIVING_WOOD.asItem());
    }
}
