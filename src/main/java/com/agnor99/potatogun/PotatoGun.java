package com.agnor99.potatogun;


import com.agnor99.potatogun.dispenser.PotatoGunDispenseBehavior;
import com.agnor99.potatogun.init.EntityInit;
import com.agnor99.potatogun.init.ItemInit;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PotatoGun.MOD_ID)
public class PotatoGun {
    public static final String MOD_ID = "potatogun";

    public PotatoGun() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityInit.ENTITY_TYPES.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);
        modEventBus.addListener(EventPriority.LOWEST, this::dispenserSetup);
    }
    private void dispenserSetup(FMLCommonSetupEvent event) {
        PotatoGunDispenseBehavior.addBehavior();
    }
}
