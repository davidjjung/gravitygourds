package com.davigj.gravity_gourds.core.registry;

import com.davigj.gravity_gourds.core.GravityGourds;
import com.teamabnormals.blueprint.core.util.registry.SoundSubRegistryHelper;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public class GGSoundEvents {
    public static final SoundSubRegistryHelper SOUNDS = GravityGourds.REGISTRY_HELPER.getSoundSubHelper();

    public static final DeferredHolder<SoundEvent, SoundEvent> GOURD_SQUISH = SOUNDS.createSoundEvent("block.gourd_squish");
}
