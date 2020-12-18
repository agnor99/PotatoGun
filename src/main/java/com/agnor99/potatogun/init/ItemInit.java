package com.agnor99.potatogun.init;

import com.agnor99.potatogun.PotatoGun;
import com.agnor99.potatogun.items.AbsorbtionItem;
import com.agnor99.potatogun.items.HarvestGunItem;
import com.agnor99.potatogun.items.PotatoGunItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PotatoGun.MOD_ID);

    public static final RegistryObject<PotatoGunItem> POTATO_GUN = ITEMS.register("potato_gun", PotatoGunItem::new);
    public static final RegistryObject<HarvestGunItem> HARVEST_GUN = ITEMS.register("harvest_gun", HarvestGunItem::new);
    public static final RegistryObject<Item> HARVESTING_AMMUNITION = ITEMS.register("harvest_ammunition", AbsorbtionItem::new);
}
