package com.agnor99.potatogun.items;

import com.agnor99.potatogun.entities.HarvestProjectileItemEntity;
import com.agnor99.potatogun.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class HarvestGunItem extends AbstractGunItem {

    @Override
    ItemStack getAndRemoveAmmunition(ServerPlayerEntity player) {
        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            if (getInventoryAmmoPredicate().test(player.inventory.getStackInSlot(i))) {

                ItemStack stack =  new ItemStack(player.inventory.getStackInSlot(i).getItem(), 1);
                player.inventory.getStackInSlot(i).setCount(player.inventory.getStackInSlot(i).getCount() - 1);
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    ProjectileItemEntity createProjectileEntity(ServerPlayerEntity player, World world, ItemStack ammunition) {
        return new HarvestProjectileItemEntity(player, world);
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("lore.potatogun.harvest_gun"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return itemStack -> itemStack.getItem() == ItemInit.HARVESTING_AMMUNITION.get();
    }

    @Override
    protected ITextComponent getNoAmmoComponent() {
        return new TranslationTextComponent("message.potatogun.harvest_gun.no_ammunition");
    }
}
