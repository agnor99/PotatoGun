package com.agnor99.potatogun.dispenser;

import com.agnor99.potatogun.CropHelper;
import com.agnor99.potatogun.PotatoGunItem;
import com.agnor99.potatogun.entities.PlantProjectileItemEntity;
import com.agnor99.potatogun.init.ItemInit;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;

public class PotatoGunDispenseBehavior extends DefaultDispenseItemBehavior {

    @Override
    protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        ItemStack seedStack = CropHelper.getCropItem((LockableLootTileEntity)source.getBlockTileEntity());
        if(seedStack.isEmpty()) return stack;
        Direction direction = source.getBlockState().get(DispenserBlock.FACING);
        PlantProjectileItemEntity projectile = new PlantProjectileItemEntity(DispenserBlock.getDispensePosition(source).getX(), DispenserBlock.getDispensePosition(source).getY(), DispenserBlock.getDispensePosition(source).getZ(), seedStack, source.getWorld());

        projectile.shoot(direction.getXOffset(), ((float)direction.getYOffset() + 0.1F), direction.getZOffset(), 1.5f, 0);

        source.getWorld().addEntity(projectile);
        return stack;
    }
    public static void addBehavior() {
        DispenserBlock.registerDispenseBehavior(ItemInit.POTATO_GUN.get(), new PotatoGunDispenseBehavior());
    }
}
