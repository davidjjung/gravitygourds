package com.davigj.gravity_gourds.core.other;

import com.davigj.gravity_gourds.core.GravityGourds;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class GGBlockTags {
    public static final TagKey<Block> POINTY = blockTag("pointy");

    private static TagKey<Block> blockTag(String name) {
        return TagUtil.blockTag(GravityGourds.MOD_ID, name);
    }
}
