package com.davigj.gravity_gourds.core.other.compat;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.sheddmer.abundant_atmosphere.common.init.AABlocks;

import static net.sheddmer.abundant_atmosphere.common.block.GourdnutBlock.HANGING;

public class AACompat {
    public static final Block gourdnut;

    public static boolean isHanging(BlockState state) {
        return state.getValue(HANGING);
    }

    public static BlockState ground(BlockState state) {
        return state.setValue(HANGING, false);
    }

    static {
        gourdnut = GGModConstants.ABUNDANTATMOSPHERE ? AABlocks.GOURDNUT.get() : null;
    }
}
