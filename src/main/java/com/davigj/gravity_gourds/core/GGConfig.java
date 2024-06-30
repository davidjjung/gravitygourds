package com.davigj.gravity_gourds.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class GGConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Integer> sliceNumber;
        public final ForgeConfigSpec.ConfigValue<Double> bonusChance;
        public final ForgeConfigSpec.ConfigValue<Integer> bonusNumber;

        Common (ForgeConfigSpec.Builder builder) {
            builder.push("pumpkins");
            sliceNumber = builder.comment("If FD is installed, how many pumpkin slices should drop").define("Slice count", 4);
            bonusChance = builder.comment("Odds of extra slices dropping").define("Bonus slice chance", 0.0);
            bonusNumber = builder.comment("Extra pumpkin slices dropped").define("Bonus slice count", 0);
            builder.pop();
        }
    }

    static final ForgeConfigSpec COMMON_SPEC;
    public static final GGConfig.Common COMMON;


    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(GGConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
