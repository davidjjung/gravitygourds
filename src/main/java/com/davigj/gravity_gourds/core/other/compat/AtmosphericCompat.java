package com.davigj.gravity_gourds.core.other.compat;

import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import com.teamabnormals.atmospheric.core.registry.AtmosphericItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class AtmosphericCompat {
    public static final Block yuccaBundle;
    public static final ItemStack yuccaFruit;
    public static final Block roastedYuccaBundle;
    public static final ItemStack roastedYuccaFruit;

    static {
        yuccaBundle = GGModConstants.ATMOSPHERIC ? AtmosphericBlocks.YUCCA_BUNDLE.get() : null;
        yuccaFruit = GGModConstants.ATMOSPHERIC ? AtmosphericItems.YUCCA_FRUIT.get().getDefaultInstance() : null;
        roastedYuccaBundle = GGModConstants.ATMOSPHERIC ? AtmosphericBlocks.ROASTED_YUCCA_BUNDLE.get() : null;
        roastedYuccaFruit = GGModConstants.ATMOSPHERIC ? AtmosphericItems.ROASTED_YUCCA_FRUIT.get().getDefaultInstance() : null;
    }
}
