package com.agnor99.potatogun.init;

import com.agnor99.potatogun.PotatoGun;
import com.agnor99.potatogun.entities.PlantProjectileItemEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, PotatoGun.MOD_ID);

    public static final RegistryObject<EntityType<PlantProjectileItemEntity>> PLANT_PROJECTILE =
            ENTITY_TYPES.register("plant_projectile",() -> EntityType.Builder.<PlantProjectileItemEntity>create(PlantProjectileItemEntity::new, EntityClassification.MISC)
                .size(0.25f,0.25f).build(new ResourceLocation(PotatoGun.MOD_ID,"plant_projectile").toString()));
}
