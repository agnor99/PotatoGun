package com.agnor99.potatogun.entities;

import com.agnor99.potatogun.CropHelper;
import com.agnor99.potatogun.init.EntityInit;
import com.agnor99.potatogun.init.ItemInit;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;

public class HarvestProjectileItemEntity extends AbstractProjectileItemEntity {
    public HarvestProjectileItemEntity(EntityType<? extends AbstractProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
        setItem(new ItemStack(ItemInit.HARVESTING_AMMUNITION.get()));
    }
    public HarvestProjectileItemEntity(LivingEntity thrower, World worldIn) {
        super(EntityInit.HARVEST_PROJECTILE.get(), thrower, worldIn);
        setItem(new ItemStack(ItemInit.HARVESTING_AMMUNITION.get()));
    }
    public HarvestProjectileItemEntity(double x,double y,double z, World worldIn) {
        super(EntityInit.HARVEST_PROJECTILE.get(),x,y,z,worldIn);
        setItem(new ItemStack(ItemInit.HARVESTING_AMMUNITION.get()));
    }

    @Override
    protected Item getDefaultItem() {
        return ItemInit.HARVESTING_AMMUNITION.get();
    }

    @Override
    protected void doWork(BlockRayTraceResult rayTraceResult) {
        BlockPos hitPos = rayTraceResult.getPos();
        List<ItemStack> drops = new ArrayList<>();
        drops.add(getItem());
        for(int x = hitPos.getX()-2; x <= hitPos.getX()+2; x++) {
            for(int z = hitPos.getZ()-2; z <= hitPos.getZ()+2; z++) {
                for(int y = hitPos.getY()+3; y > hitPos.getY()-2;y--) {
                    if(world.getBlockState(new BlockPos(x,y,z)).getBlock() instanceof CropsBlock) {
                        BlockState cropsState = world.getBlockState(new BlockPos(x,y,z));
                        if(CropHelper.isGrown(cropsState)) {
                            LootContext.Builder lootContext = (new LootContext.Builder((ServerWorld)this.world)).withRandom(this.world.rand).withParameter(LootParameters.field_237457_g_, Vector3d.copyCentered(new BlockPos(x,y,z))).withParameter(LootParameters.TOOL, ItemStack.EMPTY).withNullableParameter(LootParameters.THIS_ENTITY, this);
                            drops.addAll(cropsState.getDrops(lootContext));
                            world.setBlockState(new BlockPos(x,y,z), Blocks.AIR.getDefaultState());
                        }
                        break;
                    }
                }
            }
        }
        for(ItemStack stack: drops) {
            world.addEntity(new ItemEntity(world, getPosX(), getPosY(), getPosZ(), stack));
        }
    }
}
