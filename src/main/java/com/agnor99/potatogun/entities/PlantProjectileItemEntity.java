package com.agnor99.potatogun.entities;

import com.agnor99.potatogun.CropHelper;
import com.agnor99.potatogun.init.EntityInit;
import net.minecraft.block.AirBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;


public class PlantProjectileItemEntity extends ProjectileItemEntity {
    ItemStack seedItemStack;
    public PlantProjectileItemEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public PlantProjectileItemEntity(LivingEntity thrower, World worldIn, ItemStack itemStack) {
        super(EntityInit.PLANT_PROJECTILE.get(), thrower, worldIn);
        setItem(itemStack);
        seedItemStack = itemStack;
    }
    public PlantProjectileItemEntity(double x,double y,double z, ItemStack itemStack, World worldIn) {
        super(EntityInit.PLANT_PROJECTILE.get(),x,y,z,worldIn);
        seedItemStack = itemStack;
    }
    public ItemStack getSeedItemStack() {
        return seedItemStack;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.WHEAT_SEEDS;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if(result instanceof BlockRayTraceResult) {
            if(world instanceof ServerWorld) {
                plant(((BlockRayTraceResult) result).getPos());
            }else{
                world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), 0,0,0);
            }
            remove();
        }
    }
    @Override
    public void setItem(ItemStack itemStack) {
        super.setItem(itemStack);
        seedItemStack = itemStack;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void plant(BlockPos hitPos) {
        int plantedSeeds = 0;
        for(int x = hitPos.getX()-2; x <= hitPos.getX()+2; x++) {
            for(int z = hitPos.getZ()-2; z <= hitPos.getZ()+2; z++) {
                for(int y = hitPos.getY()+3; y > hitPos.getY()-2;y--) {
                    if(world.getBlockState(new BlockPos(x,y,z)).getBlock() instanceof FarmlandBlock &&
                            world.getBlockState(new BlockPos(x,y+1,z)).getBlock() instanceof AirBlock) {
                        world.setBlockState(new BlockPos(x,y+1,z), CropHelper.getCropsBlock(seedItemStack.getItem()).getDefaultState());
                        plantedSeeds++;
                        break;
                    }
                }
                if(getSeedItemStack().getCount() == plantedSeeds) {
                    return;
                }
            }
        }
        world.addEntity(new ItemEntity(world, getPosX(), getPosY(), getPosZ(),new ItemStack(seedItemStack.getItem(), seedItemStack.getCount()-plantedSeeds)));
    }
}
