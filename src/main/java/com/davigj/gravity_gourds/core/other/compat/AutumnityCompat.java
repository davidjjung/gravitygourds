package com.davigj.gravity_gourds.core.other.compat;

import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.world.level.block.Block;

public class AutumnityCompat {
    public static final Block largePumpkinSlice;

    static {
        largePumpkinSlice = GGModConstants.AUTUMNITY && GGModConstants.FARMERSDELIGHT ? AutumnityBlocks.LARGE_PUMPKIN_SLICE.get() : null;
    }
}
