package com.silicontent.teufel.datagen;

import com.silicontent.teufel.Teufel;
import com.silicontent.teufel.block.ModBlocks;
import com.silicontent.teufel.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

/*
NOTICE!

When using createXRecipe(), you need to chain .criterion().offerTo() or else recipe won't appear.
offerXRecipe() methods run those two methods automatically.
*/

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    // create a recipe for an ore block made of 4 pieces
    public static void offerCompactBlockRecipe(Consumer<RecipeJsonProvider> exporter, RecipeCategory compactCategory, RecipeCategory splitCategory, ItemConvertible compacted, ItemConvertible split) {
        // create block -> items recipe
        ShapedRecipeJsonBuilder.create(compactCategory, compacted, 1)
                .pattern("PP")
                .pattern("PP")
                .input('P', split)
                .criterion(hasItem(split), conditionsFromItem(split))
                .offerTo(exporter, getRecipeName(compacted));

        // create items -> block recipe
        ShapelessRecipeJsonBuilder.create(splitCategory, split, 4)
                .input(compacted)
                .criterion(hasItem(compacted), conditionsFromItem(compacted))
                .offerTo(exporter, getRecipeName(split));
    }

    // create a block -> 4 planks recipe
    public static void offerWoodPlanksRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input, String recipeID) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .input(input)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, recipeID);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        // gravel to flint recipe
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.FLINT, 1)
                .pattern("PP")
                .pattern("PP")
                .input('P', Items.GRAVEL)
                .criterion(hasItem(Items.GRAVEL), conditionsFromItem(Items.GRAVEL))
                .offerTo(exporter, "flint_from_gravel");

        // modded ingot blocks
        offerCompactBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, RecipeCategory.MISC, ModBlocks.HELLFIRE_BLOCK, ModItems.HELLFIRE_INGOT);
        offerCompactBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, RecipeCategory.MISC, ModBlocks.TERMINITE_BLOCK, ModItems.TERMINITE_INGOT);
        offerCompactBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, RecipeCategory.MISC, ModBlocks.SCULKEN_BLOCK, ModItems.SCULKEN_INGOT);
        offerCompactBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, RecipeCategory.MISC, ModBlocks.TEUFEL_BLOCK, ModItems.TEUFEL_INGOT);

        // living wood recipes
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIVING_WOOD, 3)
                .pattern("LL")
                .pattern("LL")
                .input('L', ModBlocks.LIVING_LOG)
                .criterion(hasItem(ModBlocks.LIVING_LOG), conditionsFromItem(ModBlocks.LIVING_LOG))
                .offerTo(exporter, getRecipeName(ModBlocks.LIVING_WOOD));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STRIPPED_LIVING_WOOD, 3)
                .pattern("LL")
                .pattern("LL")
                .input('L', ModBlocks.STRIPPED_LIVING_LOG)
                .criterion(hasItem(ModBlocks.STRIPPED_LIVING_LOG), conditionsFromItem(ModBlocks.STRIPPED_LIVING_LOG))
                .offerTo(exporter, getRecipeName(ModBlocks.STRIPPED_LIVING_WOOD));

        // living planks recipes
        offerWoodPlanksRecipe(exporter, ModBlocks.LIVING_PLANKS, ModBlocks.LIVING_LOG, "living_planks_from_living_log");
        offerWoodPlanksRecipe(exporter, ModBlocks.LIVING_PLANKS, ModBlocks.LIVING_WOOD, "living_planks_from_living_wood");
        offerWoodPlanksRecipe(exporter, ModBlocks.LIVING_PLANKS, ModBlocks.STRIPPED_LIVING_LOG, "living_planks_from_stripped_living_log");
        offerWoodPlanksRecipe(exporter, ModBlocks.LIVING_PLANKS, ModBlocks.STRIPPED_LIVING_WOOD, "living_planks_from_stripped_living_wood");

        createStairsRecipe(ModBlocks.LIVING_STAIRS, Ingredient.ofItems(ModBlocks.LIVING_PLANKS))
                .criterion(hasItem(ModBlocks.LIVING_PLANKS), conditionsFromItem(ModBlocks.LIVING_PLANKS))
                .offerTo(exporter, getRecipeName(ModBlocks.LIVING_STAIRS));
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIVING_SLAB, Ingredient.ofItems(ModBlocks.LIVING_PLANKS))
                .criterion(hasItem(ModBlocks.LIVING_PLANKS), conditionsFromItem(ModBlocks.LIVING_PLANKS))
                .offerTo(exporter, getRecipeName(ModBlocks.LIVING_SLAB));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ModBlocks.LIVING_BUTTON, 1)
                .input(ModBlocks.LIVING_PLANKS)
                .criterion(hasItem(ModBlocks.LIVING_PLANKS), conditionsFromItem(ModBlocks.LIVING_PLANKS))
                .offerTo(exporter, getRecipeName(ModBlocks.LIVING_BUTTON));
        createPressurePlateRecipe(RecipeCategory.REDSTONE, ModBlocks.LIVING_PRESSURE_PLATE, Ingredient.ofItems(ModBlocks.LIVING_PLANKS))
                .criterion(hasItem(ModBlocks.LIVING_PLANKS), conditionsFromItem(ModBlocks.LIVING_PLANKS))
                .offerTo(exporter, getRecipeName(ModBlocks.LIVING_PRESSURE_PLATE));
        createFenceRecipe(ModBlocks.LIVING_FENCE, Ingredient.ofItems(ModBlocks.LIVING_PLANKS))
                .criterion(hasItem(ModBlocks.LIVING_PLANKS), conditionsFromItem(ModBlocks.LIVING_PLANKS))
                .offerTo(exporter, getRecipeName(ModBlocks.LIVING_FENCE));
        createFenceGateRecipe(ModBlocks.LIVING_FENCE_GATE, Ingredient.ofItems(ModBlocks.LIVING_PLANKS))
                .criterion(hasItem(ModBlocks.LIVING_PLANKS), conditionsFromItem(ModBlocks.LIVING_PLANKS))
                .offerTo(exporter, getRecipeName(ModBlocks.LIVING_FENCE_GATE));
        createDoorRecipe(ModBlocks.LIVING_DOOR, Ingredient.ofItems(ModBlocks.LIVING_PLANKS))
                .criterion(hasItem(ModBlocks.LIVING_PLANKS), conditionsFromItem(ModBlocks.LIVING_PLANKS))
                .offerTo(exporter, getRecipeName(ModBlocks.LIVING_DOOR));
        createTrapdoorRecipe(ModBlocks.LIVING_TRAPDOOR, Ingredient.ofItems(ModBlocks.LIVING_PLANKS))
                .criterion(hasItem(ModBlocks.LIVING_PLANKS), conditionsFromItem(ModBlocks.LIVING_PLANKS))
                .offerTo(exporter, getRecipeName(ModBlocks.LIVING_TRAPDOOR));

        // hellfire ingots via smelting
        offerSmelting(exporter, List.of(ModItems.HELLFIRE_CHUNK), RecipeCategory.MISC, ModItems.HELLFIRE_INGOT, 0.7f, 200, "hellfire_ingot");
        offerBlasting(exporter, List.of(ModItems.HELLFIRE_CHUNK), RecipeCategory.MISC, ModItems.HELLFIRE_INGOT, 0.7f, 100, "hellfire_ingot");

        // reinforced stick
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.REINFORCED_STICK, 1)
                .pattern("III")
                .pattern("ISI")
                .pattern("III")
                .input('I', Items.IRON_NUGGET)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, getRecipeName(ModItems.REINFORCED_STICK));

        // ultim eye recipe
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ULTIM_EYE, 1)
                .input(Items.ENDER_EYE)
                .input(Items.DIAMOND)
                .input(Items.CHORUS_FRUIT)
                .criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .criterion(hasItem(Items.CHORUS_FRUIT), conditionsFromItem(Items.CHORUS_FRUIT))
                .offerTo(exporter, getRecipeName(ModItems.ULTIM_EYE));

        // weapons
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DIRT_BALL, 1)
                .input(ItemTags.DIRT)
                .criterion(hasItem(Items.DIRT), conditionsFromItem(Items.DIRT))
                .offerTo(exporter, getRecipeName(ModItems.DIRT_BALL));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.FLINT_DAGGER, 1)
                .pattern("F")
                .pattern("S")
                .input('F', Items.FLINT)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, getRecipeName(ModItems.FLINT_DAGGER));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.COPPER_DAGGER, 1)
                .pattern("C")
                .pattern("S")
                .input('C', Items.COPPER_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, getRecipeName(ModItems.COPPER_DAGGER));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.METEOR_SHARD, 1)
                .pattern("M")
                .pattern("M")
                .pattern("S")
                .input('M', ModItems.METEOR_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(ModItems.METEOR_INGOT), conditionsFromItem(ModItems.METEOR_INGOT))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, getRecipeName(ModItems.METEOR_SHARD));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.FROSTBITE, 1)
                .pattern("E")
                .pattern("I")
                .pattern("S")
                .input('I', Items.ICE)
                .input('E', ModItems.FROST_ESSENCE)
                .input('S', ModItems.REINFORCED_STICK)
                .criterion(hasItem(Items.ICE), conditionsFromItem(Items.ICE))
                .criterion(hasItem(ModItems.FROST_ESSENCE), conditionsFromItem(ModItems.FROST_ESSENCE))
                .criterion(hasItem(ModItems.REINFORCED_STICK), conditionsFromItem(ModItems.REINFORCED_STICK))
                .offerTo(exporter, getRecipeName(ModItems.FROSTBITE));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.OBSIDIAN_SWORD, 1)
                .pattern("F")
                .pattern("O")
                .pattern("S")
                .input('F', ModItems.OBSIDIAN_FRAGMENT)
                .input('O', Items.OBSIDIAN)
                .input('S', ModItems.REINFORCED_STICK)
                .criterion(hasItem(ModItems.OBSIDIAN_FRAGMENT), conditionsFromItem(ModItems.OBSIDIAN_FRAGMENT))
                .criterion(hasItem(Items.OBSIDIAN), conditionsFromItem(Items.OBSIDIAN))
                .criterion(hasItem(ModItems.REINFORCED_STICK), conditionsFromItem(ModItems.REINFORCED_STICK))
                .offerTo(exporter, getRecipeName(ModItems.OBSIDIAN_SWORD));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.INFERNO, 1)
                .pattern("H")
                .pattern("H")
                .pattern("S")
                .input('H', ModItems.HELLFIRE_INGOT)
                .input('S', ModItems.REINFORCED_STICK)
                .criterion(hasItem(ModItems.HELLFIRE_INGOT), conditionsFromItem(ModItems.HELLFIRE_INGOT))
                .criterion(hasItem(ModItems.REINFORCED_STICK), conditionsFromItem(ModItems.REINFORCED_STICK))
                .offerTo(exporter, getRecipeName(ModItems.INFERNO));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DEITY_BLADE, 1)
                .input(ModItems.INFERNO)
                .input(ModItems.FROSTBITE)
                .input(ModItems.LIFE_ESSENCE)
                .criterion(hasItem(ModItems.INFERNO), conditionsFromItem(ModItems.INFERNO))
                .criterion(hasItem(ModItems.FROSTBITE), conditionsFromItem(ModItems.FROSTBITE))
                .criterion(hasItem(ModItems.LIFE_ESSENCE), conditionsFromItem(ModItems.LIFE_ESSENCE))
                .offerTo(exporter, getRecipeName(ModItems.DEITY_BLADE));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ENDER_SWORD, 1)
                .pattern("E")
                .pattern("E")
                .pattern("S")
                .input('E', ModItems.ULTIM_EYE)
                .input('S', ModItems.REINFORCED_STICK)
                .criterion(hasItem(ModItems.TERMINITE_INGOT), conditionsFromItem(ModItems.TERMINITE_INGOT))
                .criterion(hasItem(ModItems.REINFORCED_STICK), conditionsFromItem(ModItems.REINFORCED_STICK))
                .offerTo(exporter, getRecipeName(ModItems.ENDER_SWORD));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TERMINUS, 1)
                .pattern("T")
                .pattern("T")
                .pattern("S")
                .input('T', ModItems.TERMINITE_INGOT)
                .input('S', ModItems.REINFORCED_STICK)
                .criterion(hasItem(ModItems.TERMINITE_INGOT), conditionsFromItem(ModItems.TERMINITE_INGOT))
                .criterion(hasItem(ModItems.REINFORCED_STICK), conditionsFromItem(ModItems.REINFORCED_STICK))
                .offerTo(exporter, getRecipeName(ModItems.TERMINUS));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DEEPSLASH, 1)
                .pattern("C")
                .pattern("C")
                .pattern("S")
                .input('C', ModItems.SCULKEN_INGOT)
                .input('S', ModItems.REINFORCED_STICK)
                .criterion(hasItem(ModItems.SCULKEN_INGOT), conditionsFromItem(ModItems.SCULKEN_INGOT))
                .criterion(hasItem(ModItems.REINFORCED_STICK), conditionsFromItem(ModItems.REINFORCED_STICK))
                .offerTo(exporter, getRecipeName(ModItems.DEEPSLASH));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TEUFEL, 1)
                .pattern("T")
                .pattern("T")
                .pattern("S")
                .input('T', ModItems.TEUFEL_INGOT)
                .input('S', ModItems.REINFORCED_STICK)
                .criterion(hasItem(ModItems.TEUFEL_INGOT), conditionsFromItem(ModItems.TEUFEL_INGOT))
                .criterion(hasItem(ModItems.REINFORCED_STICK), conditionsFromItem(ModItems.REINFORCED_STICK))
                .offerTo(exporter, getRecipeName(ModItems.TEUFEL));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ULTIMATE_HOE, 1)
                .input(Items.NETHER_STAR)
                .input(ItemTags.HOES)
                .input(ModItems.LIFE_ESSENCE)
                .input(ModItems.DEATH_ESSENCE)
                .input(ModItems.PEACE_ESSENCE)
                .input(ModItems.PAIN_ESSENCE)
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .criterion(hasItem(ModItems.LIFE_ESSENCE), conditionsFromItem(ModItems.LIFE_ESSENCE))
                .criterion(hasItem(ModItems.DEATH_ESSENCE), conditionsFromItem(ModItems.DEATH_ESSENCE))
                .criterion(hasItem(ModItems.PEACE_ESSENCE), conditionsFromItem(ModItems.PEACE_ESSENCE))
                .criterion(hasItem(ModItems.PAIN_ESSENCE), conditionsFromItem(ModItems.PAIN_ESSENCE))
                .offerTo(exporter, getRecipeName(ModItems.ULTIMATE_HOE));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.COPPER_INJECTION, 1)
                .pattern("S")
                .pattern("C")
                .pattern("G")
                .input('S', Items.NETHER_STAR)
                .input('C', Items.COPPER_BLOCK)
                .input('G', Items.GLASS)
                .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                .criterion(hasItem(Items.COPPER_BLOCK), conditionsFromItem(Items.COPPER_BLOCK))
                .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
                .offerTo(exporter, getRecipeName(ModItems.COPPER_INJECTION));

        // totems
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TOTEM, 1)
                .pattern(" B ")
                .pattern("ETE")
                .pattern(" B ")
                .input('B', Items.COBBLESTONE)
                .input('E', Items.LAPIS_LAZULI)
                .input('T', Items.EXPERIENCE_BOTTLE)
                .criterion(hasItem(Items.COBBLESTONE), conditionsFromItem(Items.COBBLESTONE))
                .criterion(hasItem(Items.LAPIS_LAZULI), conditionsFromItem(Items.LAPIS_LAZULI))
                .criterion(hasItem(Items.EXPERIENCE_BOTTLE), conditionsFromItem(Items.EXPERIENCE_BOTTLE))
                .offerTo(exporter, getRecipeName(ModItems.TOTEM));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.TOTEM_OF_UNDYING, 1)
                .pattern(" E ")
                .pattern("MTM")
                .pattern(" B ")
                .input('E', ModItems.LIFE_ESSENCE)
                .input('M', Items.EMERALD)
                .input('T', ModItems.TOTEM)
                .input('B', Items.GOLD_BLOCK)
                .criterion(hasItem(ModItems.LIFE_ESSENCE), conditionsFromItem(ModItems.LIFE_ESSENCE))
                .criterion(hasItem(Items.EMERALD), conditionsFromItem(Items.EMERALD))
                .criterion(hasItem(ModItems.TOTEM), conditionsFromItem(ModItems.TOTEM))
                .criterion(hasItem(Items.GOLD_BLOCK), conditionsFromItem(Items.GOLD_BLOCK))
                .offerTo(exporter, new Identifier(Teufel.MOD_ID + ":totem_of_undying_recipe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.SPEED_TOTEM, 1)
                .pattern(" E ")
                .pattern("MTM")
                .pattern(" B ")
                .input('E', ModItems.PEACE_ESSENCE)
                .input('M', Items.EMERALD)
                .input('T', ModItems.TOTEM)
                .input('B', Items.DIAMOND_BLOCK)
                .criterion(hasItem(ModItems.PEACE_ESSENCE), conditionsFromItem(ModItems.PEACE_ESSENCE))
                .criterion(hasItem(Items.EMERALD), conditionsFromItem(Items.EMERALD))
                .criterion(hasItem(ModItems.TOTEM), conditionsFromItem(ModItems.TOTEM))
                .criterion(hasItem(Items.DIAMOND_BLOCK), conditionsFromItem(Items.DIAMOND_BLOCK))
                .offerTo(exporter, getRecipeName(ModItems.SPEED_TOTEM));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STRENGTH_TOTEM, 1)
                .pattern(" E ")
                .pattern("MTM")
                .pattern(" B ")
                .input('E', ModItems.DEATH_ESSENCE)
                .input('M', Items.DIAMOND)
                .input('T', ModItems.TOTEM)
                .input('B', ModBlocks.HELLFIRE_BLOCK)
                .criterion(hasItem(ModItems.DEATH_ESSENCE), conditionsFromItem(ModItems.DEATH_ESSENCE))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .criterion(hasItem(ModItems.TOTEM), conditionsFromItem(ModItems.TOTEM))
                .criterion(hasItem(ModBlocks.HELLFIRE_BLOCK), conditionsFromItem(ModBlocks.HELLFIRE_BLOCK))
                .offerTo(exporter, getRecipeName(ModItems.STRENGTH_TOTEM));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.JUMP_TOTEM, 1)
                .pattern(" E ")
                .pattern("MTM")
                .pattern(" B ")
                .input('E', ModItems.PEACE_ESSENCE)
                .input('M', Items.IRON_INGOT)
                .input('T', ModItems.TOTEM)
                .input('B', Items.EMERALD_BLOCK)
                .criterion(hasItem(ModItems.PEACE_ESSENCE), conditionsFromItem(ModItems.PEACE_ESSENCE))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(ModItems.TOTEM), conditionsFromItem(ModItems.TOTEM))
                .criterion(hasItem(Items.EMERALD_BLOCK), conditionsFromItem(Items.EMERALD_BLOCK))
                .offerTo(exporter, getRecipeName(ModItems.JUMP_TOTEM));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.SAFETY_TOTEM, 1)
                .pattern(" E ")
                .pattern("MTM")
                .pattern(" B ")
                .input('E', ModItems.PAIN_ESSENCE)
                .input('M', Items.CHORUS_FRUIT)
                .input('T', ModItems.TOTEM)
                .input('B', Items.AMETHYST_BLOCK)
                .criterion(hasItem(ModItems.PAIN_ESSENCE), conditionsFromItem(ModItems.PAIN_ESSENCE))
                .criterion(hasItem(Items.CHORUS_FRUIT), conditionsFromItem(Items.CHORUS_FRUIT))
                .criterion(hasItem(ModItems.TOTEM), conditionsFromItem(ModItems.TOTEM))
                .criterion(hasItem(Items.AMETHYST_BLOCK), conditionsFromItem(Items.AMETHYST_BLOCK))
                .offerTo(exporter, getRecipeName(ModItems.SAFETY_TOTEM));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.MOVE_TOTEM, 1)
                .input(ModItems.SPEED_TOTEM)
                .input(ModItems.STRENGTH_TOTEM)
                .input(ModItems.JUMP_TOTEM)
                .input(ModItems.PEACE_ESSENCE)
                .criterion(hasItem(ModItems.SPEED_TOTEM), conditionsFromItem(ModItems.SPEED_TOTEM))
                .criterion(hasItem(ModItems.STRENGTH_TOTEM), conditionsFromItem(ModItems.STRENGTH_TOTEM))
                .criterion(hasItem(ModItems.JUMP_TOTEM), conditionsFromItem(ModItems.JUMP_TOTEM))
                .criterion(hasItem(ModItems.PEACE_ESSENCE), conditionsFromItem(ModItems.PEACE_ESSENCE))
                .offerTo(exporter, getRecipeName(ModItems.MOVE_TOTEM));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.WATER_TOTEM, 1)
                .pattern(" E ")
                .pattern("MTM")
                .pattern(" B ")
                .input('E', ModItems.FROST_ESSENCE)
                .input('M', Items.GOLD_INGOT)
                .input('T', ModItems.TOTEM)
                .input('B', Items.LAPIS_BLOCK)
                .criterion(hasItem(ModItems.FROST_ESSENCE), conditionsFromItem(ModItems.FROST_ESSENCE))
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(ModItems.TOTEM), conditionsFromItem(ModItems.TOTEM))
                .criterion(hasItem(Items.LAPIS_BLOCK), conditionsFromItem(Items.LAPIS_BLOCK))
                .offerTo(exporter, getRecipeName(ModItems.WATER_TOTEM));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DEATH_TOTEM, 1)
                .pattern(" E ")
                .pattern("MTM")
                .pattern(" B ")
                .input('E', ModItems.DEATH_ESSENCE)
                .input('M', Items.DIAMOND)
                .input('T', ModItems.TOTEM)
                .input('B', Items.REDSTONE_BLOCK)
                .criterion(hasItem(ModItems.DEATH_ESSENCE), conditionsFromItem(ModItems.DEATH_ESSENCE))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .criterion(hasItem(ModItems.TOTEM), conditionsFromItem(ModItems.TOTEM))
                .criterion(hasItem(Items.REDSTONE_BLOCK), conditionsFromItem(Items.REDSTONE_BLOCK))
                .offerTo(exporter, getRecipeName(ModItems.DEATH_TOTEM));
    }
}
