package com.agnor99.potatogun.entities;

import com.agnor99.potatogun.init.EntityInit;
import com.agnor99.potatogun.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractProjectileItemEntity extends ProjectileItemEntity {

    public AbstractProjectileItemEntity(EntityType<? extends AbstractProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }
    public AbstractProjectileItemEntity(EntityType<? extends AbstractProjectileItemEntity> type, LivingEntity thrower, World worldIn) {
        super(type, thrower, worldIn);
    }
    public AbstractProjectileItemEntity(EntityType<? extends AbstractProjectileItemEntity> type, double x, double y, double z, World worldIn) {
        super(type, x, y, z, worldIn);
    }
    protected abstract Item getDefaultItem();

    @Override
    protected void onImpact(RayTraceResult result) {
        if(result instanceof BlockRayTraceResult) {
            if(world instanceof ServerWorld) {
                doWork((BlockRayTraceResult)result);
            }else{
                world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), 0,0,0);
            }
            remove();
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    protected abstract void doWork(BlockRayTraceResult rayTraceResult);

}
