package com.silicontent.teufel.item;

import com.silicontent.teufel.Teufel;
import com.silicontent.teufel.item.custom.TooltipItem;
import com.silicontent.teufel.item.weapons.*;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
	// miscellaneous items
	public static final Item REINFORCED_STICK = registerItem("reinforced_stick", new Item(new Item.Settings()));
	public static final Item WITHERED_BONE = registerItem("withered_bone", new Item(new Item.Settings()));
	public static final Item OBSIDIAN_FRAGMENT = registerItem("obsidian_fragment", new Item(new Item.Settings()));
	public static final Item ULTIM_EYE = registerItem("ultim_eye", new TooltipItem(new Item.Settings().rarity(Rarity.UNCOMMON), "tooltip.teufel.ultim_eye"));

	// essences
	public static final Item LIFE_ESSENCE = registerItem("life_essence", new TooltipItem(new Item.Settings(), "tooltip.teufel.life_essence"));
	public static final Item DEATH_ESSENCE = registerItem("death_essence", new TooltipItem(new Item.Settings(), "tooltip.teufel.death_essence"));
	public static final Item FIRE_ESSENCE = registerItem("fire_essence", new TooltipItem(new Item.Settings(), "tooltip.teufel.fire_essence"));
	public static final Item WATER_ESSENCE = registerItem("water_essence", new TooltipItem(new Item.Settings(), "tooltip.teufel.water_essence"));
	public static final Item PEACE_ESSENCE = registerItem("peace_essence", new TooltipItem(new Item.Settings(), "tooltip.teufel.peace_essence"));
	public static final Item PAIN_ESSENCE = registerItem("pain_essence", new TooltipItem(new Item.Settings(), "tooltip.teufel.pain_essence"));

	// ingots and raw ore
	public static final Item HELLFIRE_CHUNK = registerItem("hellfire_chunk", new Item(new Item.Settings()));
	public static final Item METEOR_INGOT = registerItem("meteor_ingot", new Item(new Item.Settings()));
	public static final Item HELLFIRE_INGOT = registerItem("hellfire_ingot", new Item(new Item.Settings()));
	public static final Item TERMINITE_INGOT = registerItem("terminite_ingot", new Item(new Item.Settings()));
	public static final Item SCULKEN_INGOT = registerItem("sculken_ingot", new Item(new Item.Settings()));
	public static final Item DEMONITE_INGOT = registerItem("demonite_ingot", new Item(new Item.Settings()));

	// weapons
	public static final Item DIRT_BALL = registerItem("dirt_ball",
			new DirtBallItem(ModToolMaterial.DIRT, 1, -0.5f, new Item.Settings().maxDamage(50)));
	public static final Item FLINT_DAGGER = registerItem("flint_dagger",
			new FlintDaggerItem(ModToolMaterial.DIRT, 2, -2.0f, new Item.Settings().maxDamage(75)));
	public static final Item COPPER_DAGGER = registerItem("copper_dagger",
			new CopperDaggerItem(ModToolMaterial.COPPER, 3, -2.0f, new Item.Settings().maxDamage(185)));
	public static final Item METEOR_SHARD = registerItem("meteor_shard",
			new MeteorShardItem(ModToolMaterial.METEOR, 5, -2.4f, new Item.Settings().maxDamage(200)));
	public static final Item FROSTBITE = registerItem("frostbite",
			new FrostbiteItem(ModToolMaterial.FROZEN, 8, -2.4f, new Item.Settings().maxDamage(823)));
	public static final Item OBSIDIAN_SWORD = registerItem("obsidian_sword",
			new SwordItem(ModToolMaterial.OBSIDIAN, 11, -2.6f, new Item.Settings().maxDamage(1802)));
	public static final Item PIGLIN_WARAXE = registerItem("piglin_waraxe",
			new AxeItem(ModToolMaterial.WARAXE, 14, -3.0f, new Item.Settings().maxDamage(2133)));
	public static final Item INFERNO = registerItem("inferno",
			new SwordItem(ModToolMaterial.HELLFIRE, 16, -2.4f, new Item.Settings().maxDamage(2955)));
	public static final Item DEITY_BLADE = registerItem("deity_blade",
			new SwordItem(ModToolMaterial.HELLFIRE, 21, -2.4f, new Item.Settings().maxDamage(3084)));
	public static final Item ENDER_SWORD = registerItem("ender_sword",
			new SwordItem(ModToolMaterial.TERMINITE, 26, -2.4f, new Item.Settings().maxDamage(4241)));
	public static final Item TERMINUS = registerItem("terminus",
			new SwordItem(ModToolMaterial.TERMINITE, 29, -2.4f, new Item.Settings().maxDamage(4112)));
	public static final Item DEEPSLASH = registerItem("deepslash",
			new SwordItem(ModToolMaterial.SCULKEN, 37, -2.4f, new Item.Settings().maxDamage(5037)));
	public static final Item TEUFEL = registerItem("teufel",
			new SwordItem(ModToolMaterial.DEMONITE, 59, -2.4f, new Item.Settings().maxDamage(6660)));
	public static final Item ULTIMATE_HOE = registerItem("ultimate_hoe",
			new HoeItem(ModToolMaterial.ENDGAME, 69419, -2.0f, new Item.Settings()));
	public static final Item COPPER_INJECTION = registerItem("copper_injection",
			new SwordItem(ModToolMaterial.ENDGAME, 69419, -2.0f, new Item.Settings()));

	private static Item registerItem(String name, Item item) {
		// register a given mod item into the item registry
		return Registry.register(Registries.ITEM, new Identifier(Teufel.MOD_ID, name), item);
	}

	public static void registerModItems() {
		Teufel.LOGGER.info("Registering mod items for " + Teufel.MOD_ID);

		//ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
	}
}
