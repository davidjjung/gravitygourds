package com.davigj.gravity_gourds.core.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockBehaviour.class)
public interface IBlockBehaviourAccessor {
    @Invoker
    boolean callCanSurvive(BlockState state, LevelReader level, BlockPos pos);
}
