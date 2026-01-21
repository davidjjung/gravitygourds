package com.davigj.gravity_gourds.core.other.compat;

import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FDCompat {
    public static final ItemStack pumpkinSlice;

    static {
        pumpkinSlice = GGModConstants.FARMERSDELIGHT ? new ItemStack(ModItems.PUMPKIN_SLICE.get()) : null;
    }
}
