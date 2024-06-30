package com.davigj.gravity_gourds.core.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "com.teamabnormals.autumnity.common.block.LargePumpkinSliceBlock")
public abstract class LargePumpkinSliceBlockMixin extends Block implements Fallable {
    public LargePumpkinSliceBlockMixin(Properties p_49795_) {
        super(p_49795_);
    }

    public void animateTick(BlockState p_221129_, Level p_221130_, BlockPos p_221131_, RandomSource p_221132_) {
    }

    protected int getDelayAfterPlace() {
        return 2;
    }

    private static boolean isFree(BlockState p_53242_) {
        Material material = p_53242_.getMaterial();
        return p_53242_.isAir() || p_53242_.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    public void onPlace(BlockState p_53233_, Level p_53234_, BlockPos p_53235_, BlockState p_53236_, boolean p_53237_) {
        p_53234_.scheduleTick(p_53235_, this, this.getDelayAfterPlace());
    }

    public BlockState updateShape(BlockState p_53226_, Direction p_53227_, BlockState p_53228_, LevelAccessor p_53229_, BlockPos p_53230_, BlockPos p_53231_) {
        p_53229_.scheduleTick(p_53230_, this, this.getDelayAfterPlace());
        return super.updateShape(p_53226_, p_53227_, p_53228_, p_53229_, p_53230_, p_53231_);
    }

    public void tick(BlockState p_221124_, ServerLevel p_221125_, BlockPos p_221126_, RandomSource p_221127_) {
        if (isFree(p_221125_.getBlockState(p_221126_.below())) && p_221126_.getY() >= p_221125_.getMinBuildHeight()) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(p_221125_, p_221126_, p_221124_);
            this.falling(fallingblockentity);
        }
    }

    protected void falling(FallingBlockEntity p_53206_) {
    }
}
