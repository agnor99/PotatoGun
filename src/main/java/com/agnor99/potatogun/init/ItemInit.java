package com.agnor99.potatogun.init;

import com.agnor99.potatogun.PotatoGun;
import com.agnor99.potatogun.PotatoGunItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PotatoGun.MOD_ID);

    public static final RegistryObject<PotatoGunItem> POTATO_GUN = ITEMS.register("potato_gun", PotatoGunItem::new);

}
