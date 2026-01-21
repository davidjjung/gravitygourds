package com.davigj.gravity_gourds.core;

import com.davigj.gravity_gourds.core.other.GGPistonOverrides;
import com.davigj.gravity_gourds.core.registry.GGSoundEvents;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(GravityGourds.MOD_ID)
public class GravityGourds {
    public static final String MOD_ID = "gravity_gourds";
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

    public GravityGourds(IEventBus bus, ModContainer container) {
        GGSoundEvents.SOUNDS.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);

        container.registerConfig(ModConfig.Type.COMMON, GGConfig.COMMON_SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(GGPistonOverrides::init);
    }

    private void clientSetup(FMLClientSetupEvent event) {
    }

    private void dataSetup(GatherDataEvent event) {
    }

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}