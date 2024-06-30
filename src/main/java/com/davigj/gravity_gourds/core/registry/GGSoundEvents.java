package com.davigj.gravity_gourds.core.registry;

import com.davigj.gravity_gourds.core.GravityGourds;
import com.teamabnormals.blueprint.core.util.registry.SoundSubRegistryHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GGSoundEvents {
    private static final SoundSubRegistryHelper HELPER = GravityGourds.REGISTRY_HELPER.getSoundSubHelper();

    public static final RegistryObject<SoundEvent> GOURD_SQUISH = HELPER.createSoundEvent("block.gourd_squish");
}
