package com.silicontent.teufel.datagen;

import com.silicontent.teufel.Teufel;
import com.silicontent.teufel.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    // tags that contain all the blocks that can be used when creating a furnace
    // TODO: replace nether materials in these tags with a whole new upgraded furnace
    // TODO: alternatively, remove these tags and only allow cobbled deepslate and cobblestone
    public static final TagKey<Item> INNER_FURNACE_STONES = TagKey.of(RegistryKeys.ITEM, new Identifier(Teufel.MOD_ID, "inner_furnace_stones"));
    public static final TagKey<Item> CORNER_FURNACE_STONES = TagKey.of(RegistryKeys.ITEM, new Identifier(Teufel.MOD_ID, "corner_furnace_stones"));


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

        // blocks that can be used to create a furnace
        getOrCreateTagBuilder(INNER_FURNACE_STONES)
                .add(Blocks.COBBLESTONE.asItem())
                .add(Blocks.STONE.asItem())
                .add(Blocks.NETHERRACK.asItem());
        getOrCreateTagBuilder(CORNER_FURNACE_STONES)
                .add(Blocks.COBBLED_DEEPSLATE.asItem())
                .add(Blocks.DEEPSLATE.asItem())
                .add(Blocks.BLACKSTONE.asItem());
    }
}
