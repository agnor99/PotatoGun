package com.agnor99.potatogun.items;

import com.agnor99.potatogun.CropHelper;
import com.agnor99.potatogun.entities.PlantProjectileItemEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class PotatoGunItem extends AbstractGunItem {

    public static final Predicate<ItemStack> SEEDS = itemStack -> CropHelper.isCropItem(itemStack.getItem());

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Override
    ItemStack getAndRemoveAmmunition(ServerPlayerEntity player) {
        return CropHelper.getCropItem(player.inventory);
    }

    @Override
    ProjectileItemEntity createProjectileEntity(ServerPlayerEntity player, World world, ItemStack ammunition) {
        return new PlantProjectileItemEntity(player, world, ammunition);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("lore.potatogun.potato_gun"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return SEEDS;
    }

    @Override
    public int func_230305_d_() {
        return 15;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        super.getUseDuration(stack);
        return 72000;
    }
    @Override
    protected ITextComponent getNoAmmoComponent() {
        return new TranslationTextComponent("message.potatogun.potato_gun.no_ammunition");
    }
}
