package com.davigj.gravity_gourds.core.other;

import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
import vectorwing.farmersdelight.common.registry.ModItems;

public class GGConstants {
    public static final ItemStack pumpkinSlice;
    public static final Block largePumpkinSlice;
    public static final Block yuccaBundle;
    public static final Block roastedYuccaBundle;

    static {
        pumpkinSlice = ModList.get().isLoaded("farmersdelight") ? new ItemStack(ModItems.PUMPKIN_SLICE.get()) : null;
        largePumpkinSlice = ModList.get().isLoaded("autumnity") &&
                ModList.get().isLoaded("farmersdelight") ? AutumnityBlocks.LARGE_PUMPKIN_SLICE.get() : null;
        yuccaBundle = ModList.get().isLoaded("atmospheric") ? AtmosphericBlocks.YUCCA_BUNDLE.get() : null;
        roastedYuccaBundle = ModList.get().isLoaded("atmospheric") ? AtmosphericBlocks.ROASTED_YUCCA_BUNDLE.get() : null;
    }
}
