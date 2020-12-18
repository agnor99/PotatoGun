package com.agnor99.potatogun.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.function.Predicate;

public abstract class AbstractGunItem extends ShootableItem {

    public AbstractGunItem() {
        super(new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entityLiving;
            ItemStack ammunition = getAndRemoveAmmunition(player);

            if (ammunition.getCount() == 0) {
                player.sendMessage(getNoAmmoComponent(), null);
                return;
            }

            ProjectileItemEntity projectile = createProjectileEntity(player, worldIn, ammunition);

            projectile.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 0F);

            worldIn.addEntity(projectile);
        }
    }

    abstract ItemStack getAndRemoveAmmunition(ServerPlayerEntity player);
    abstract ProjectileItemEntity createProjectileEntity(ServerPlayerEntity player, World world, ItemStack ammunition);

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return null;
    }

    @Override
    public int func_230305_d_() {
        return 15;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    abstract protected ITextComponent getNoAmmoComponent();
}
