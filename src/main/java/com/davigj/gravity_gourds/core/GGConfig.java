package com.davigj.gravity_gourds.core;


import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class GGConfig {
    public static class Common {
        public final ModConfigSpec.ConfigValue<Integer> sliceNumber;
        public final ModConfigSpec.ConfigValue<Double> bonusChance;
        public final ModConfigSpec.ConfigValue<Integer> bonusNumber;
        public final ModConfigSpec.ConfigValue<Boolean> hurtEntities;

        Common (ModConfigSpec.Builder builder) {
            builder.push("common");
            hurtEntities = builder.comment("Falling gourds hurt entities").define("Gourds clonk", true);
            builder.push("pumpkins");
            sliceNumber = builder.comment("If FD is installed, how many pumpkin slices should drop").define("Slice count", 4);
            bonusChance = builder.comment("Odds of extra slices dropping").define("Bonus slice chance", 0.0);
            bonusNumber = builder.comment("Extra pumpkin slices dropped").define("Bonus slice count", 0);
            builder.pop();
            builder.pop();
        }
    }

    public static final ModConfigSpec COMMON_SPEC;
    public static final GGConfig.Common COMMON;


    static {
        final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(GGConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
