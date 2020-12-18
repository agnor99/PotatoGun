package com.agnor99.potatogun.client;

import com.agnor99.potatogun.entities.HarvestProjectileItemEntity;
import com.agnor99.potatogun.entities.PlantProjectileItemEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class HarvestProjectileItemEntityRenderer extends SpriteRenderer<HarvestProjectileItemEntity> {
    public HarvestProjectileItemEntityRenderer(EntityRendererManager p_i226035_1_, ItemRenderer p_i226035_2_) {
        super(p_i226035_1_, p_i226035_2_);
    }
    public static class Factory implements IRenderFactory<HarvestProjectileItemEntity>{

        @Override
        public EntityRenderer<? super HarvestProjectileItemEntity> createRenderFor(EntityRendererManager manager) {
            return new HarvestProjectileItemEntityRenderer(manager, Minecraft.getInstance().getItemRenderer());
        }
    }
}
