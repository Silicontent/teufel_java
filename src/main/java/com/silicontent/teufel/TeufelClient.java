package com.silicontent.teufel;

import com.silicontent.teufel.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class TeufelClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// enabling transparency on doors and trapdoors
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIVING_DOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIVING_TRAPDOOR, RenderLayer.getCutout());

		// register entities
		//EntityRendererRegistry.register(ModEntities.HOSTILE_COW, CowEntityRenderer::new);
	}
}
