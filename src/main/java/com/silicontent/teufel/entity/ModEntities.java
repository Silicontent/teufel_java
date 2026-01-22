package com.silicontent.teufel.entity;

import com.silicontent.teufel.Teufel;
import com.silicontent.teufel.entity.custom.HostileCowEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
	// modded entities


	// tweaked vanilla entities
	public static final EntityType<HostileCowEntity> HOSTILE_COW = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(Teufel.MOD_ID, "cow"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HostileCowEntity::new)
					.dimensions(EntityDimensions.fixed(0.9f, 1.4f))
					.build()
	);

	public static void registerModEntities() {
		Teufel.LOGGER.info("registering mod entities for " + Teufel.MOD_ID);

		// registering mobs
		FabricDefaultAttributeRegistry.register(ModEntities.HOSTILE_COW, HostileCowEntity.createCowAttributes());
	}
}
