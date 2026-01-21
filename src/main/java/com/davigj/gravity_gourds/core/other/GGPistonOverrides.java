package com.davigj.gravity_gourds.core.other;

import com.davigj.gravity_gourds.core.other.compat.GGModConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.fml.util.ObfuscationReflectionHelper;

public class GGPistonOverrides {
    public static void init() {
        registerPistonOverrides();
    }

    public static void registerPistonOverrides(){
        ResourceLocation melon = ResourceLocation.fromNamespaceAndPath("minecraft", "melon");
        ResourceLocation pumpkin = ResourceLocation.fromNamespaceAndPath("minecraft", "pumpkin");
        Block melonBlock = BuiltInRegistries.BLOCK.get(melon);
        Block pumpkinBlock = BuiltInRegistries.BLOCK.get(pumpkin);

        if (melonBlock != null && pumpkinBlock != null) {
            ObfuscationReflectionHelper.setPrivateValue(BlockBehaviour.BlockStateBase.class, melonBlock.defaultBlockState(), PushReaction.NORMAL, "pushReaction");
            ObfuscationReflectionHelper.setPrivateValue(BlockBehaviour.BlockStateBase.class, pumpkinBlock.defaultBlockState(), PushReaction.NORMAL, "pushReaction");
        }

        if (GGModConstants.ATMOSPHERIC) {
            ResourceLocation yucca = ResourceLocation.fromNamespaceAndPath("atmospheric", "yucca_bundle");
            Block yuccaBlock = BuiltInRegistries.BLOCK.get(yucca);
            if (yuccaBlock != null) {
                ObfuscationReflectionHelper.setPrivateValue(BlockBehaviour.BlockStateBase.class, yuccaBlock.defaultBlockState(), PushReaction.NORMAL, "pushReaction");
            }
        }

        if (GGModConstants.ABUNDANTATMOSPHERE) {
            ResourceLocation gourdnut = ResourceLocation.fromNamespaceAndPath("abundant_atmosphere", "gourdnut");
            Block gourdnutBlock = BuiltInRegistries.BLOCK.get(gourdnut);
            if (gourdnutBlock != null) {
                ObfuscationReflectionHelper.setPrivateValue(BlockBehaviour.BlockStateBase.class, gourdnutBlock.defaultBlockState(), PushReaction.NORMAL, "pushReaction");
            }
        }
    }
}
