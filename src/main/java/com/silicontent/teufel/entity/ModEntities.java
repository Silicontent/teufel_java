package com.silicontent.teufel.entity;

import com.silicontent.teufel.Teufel;

public class ModEntities {
//	public static final EntityType<HostileCowEntity> HOSTILE_COW = Registry.register(
//			Registries.ENTITY_TYPE,
//			new Identifier(Teufel.MOD_ID, "cow"),
//			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HostileCowEntity::new)
//					.dimensions(EntityDimensions.fixed(0.9f, 1.4f))
//					.build()
//	);

	public static void registerModEntities() {
		Teufel.LOGGER.info("registering mod entities for " + Teufel.MOD_ID);

		// registering mobs
		//FabricDefaultAttributeRegistry.register(ModEntities.HOSTILE_COW, HostileCowEntity.createCowAttributes());
	}
}
