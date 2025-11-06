package com.silicontent.teufel.item;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;

import java.util.function.Supplier;

public enum ModToolMaterial implements ToolMaterial {
    DIRT(-1, 50, 1.0f, 0.0f, 0,
            () -> Ingredient.fromTag(ItemTags.DIRT)),
    COPPER(0, 346, 1.0f, 0.0f, 20,
                 () -> Ingredient.ofItems(Items.COPPER_INGOT)),
    METEOR(2, 891, 1.0f, 0.0f, 5,
                   () -> Ingredient.ofItems(ModItems.METEOR_INGOT)),
    FROSTBITE(2, 1433, 1.0f, 0.0f, 12,
                   () -> Ingredient.ofItems(Items.ICE)),
    OBSIDIAN(3, 3012, 1.0f, 0.0f, 15,
                      () -> Ingredient.ofItems(Items.OBSIDIAN, Items.CRYING_OBSIDIAN)),
    WARAXE(4, 2937, 1.0f, 0.0f, 15,
                     () -> Ingredient.ofItems(Items.GOLD_INGOT)),
    HELLFIRE(4, 2955, 1.0f, 0.0f, 20,
            () -> Ingredient.ofItems(ModItems.HELLFIRE_INGOT)),
    TERMINITE(5, 4112, 1.0f, 0.0f, 35,
                     () -> Ingredient.ofItems(ModItems.TERMINITE_INGOT)),
    SCULKEN(6, 5037, 1.0f, 0.0f, 40,
                      () -> Ingredient.ofItems(ModItems.SCULKEN_INGOT)),
    TEUFEL(7, 6660, 1.0f, 0.0f, 50,
                    () -> Ingredient.ofItems(ModItems.TEUFEL_INGOT)),
    ENDGAME(7, 99999, 1.0f, 0.0f, 50,
                   () -> Ingredient.ofItems(Items.NETHER_STAR));

    // tool tier
    private final int miningLevel;
    // amount of tool durability
    private final int durability;
    // multiplier for mining speed
    private final float miningSpeed;
    // attack damage adder
    private final float attackDamage;
    // determines quality of enchantments on this tier
    private final int enchantability;
    // item used to repair tool via anvil
    private final Supplier<Ingredient> repairIngredient;


    ModToolMaterial(int miningLevel, int durability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
