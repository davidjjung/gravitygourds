package com.davigj.gravity_gourds.core.other;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class GGCompat {
    public static void init() {
        registerPistonOverrides();
    }

    public static void registerPistonOverrides(){
        Block melon = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:melon"));
        Block pumpkin = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:pumpkin"));
        if (melon != null && pumpkin != null) {
            ObfuscationReflectionHelper.setPrivateValue(BlockBehaviour.BlockStateBase.class, melon.defaultBlockState(), PushReaction.NORMAL, "f_278134_");
            ObfuscationReflectionHelper.setPrivateValue(BlockBehaviour.BlockStateBase.class, pumpkin.defaultBlockState(), PushReaction.NORMAL, "f_278134_");
        }
    }
}
