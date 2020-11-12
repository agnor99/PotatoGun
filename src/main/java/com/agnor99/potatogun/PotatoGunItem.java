package com.agnor99.potatogun;

import com.agnor99.potatogun.entities.PlantProjectileItemEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class PotatoGunItem extends ShootableItem {

    public static final Predicate<ItemStack> SEEDS = (itemStack) -> CropHelper.isCropItem(itemStack.getItem());

    public PotatoGunItem() {
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
            ItemStack seedItem = CropHelper.getCropItem(player.inventory);

            if (seedItem.getCount() == 0) {
                player.sendMessage(new TranslationTextComponent("message.potatogun.no_seed"));
                return;
            }

            PlantProjectileItemEntity projectile = new PlantProjectileItemEntity(player, worldIn, seedItem);

            projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 0F);

            worldIn.addEntity(projectile);
        }
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
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

}
