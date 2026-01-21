package com.davigj.gravity_gourds.core.mixin;

import com.davigj.gravity_gourds.core.other.GGBlockTags;
import com.davigj.gravity_gourds.core.other.compat.AACompat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.davigj.gravity_gourds.core.other.compat.GGModConstants.ABUNDANTATMOSPHERE;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {
    @Unique
    protected int gravitygourds$getDelayAfterPlace() {
        return 2;
    }

    @Inject(method = "onPlace", at = @At("HEAD"))
    public void placeToBeOverriden(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston, CallbackInfo ci) {
        if (((Block)(Object)this).defaultBlockState().is(GGBlockTags.GOURDS)) {
            boolean ripe = true;
            if (ABUNDANTATMOSPHERE) {
                if (((Block)(Object)this).defaultBlockState().is(AACompat.gourdnut) && ((IBlockBehaviourAccessor)this).callCanSurvive(state, level, pos)) {
                    ripe = false;
                }
            }
            if (ripe) {
                level.scheduleTick(pos, (Block) (Object) this, this.gravitygourds$getDelayAfterPlace());
            }
        }
    }

    @Inject(method = "updateShape", at = @At("HEAD"))
    private void updoot(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if (((Block)(Object)this).defaultBlockState().is(GGBlockTags.GOURDS)) {
            boolean ripe = true;
            if (ABUNDANTATMOSPHERE) {
                if (((Block)(Object)this).defaultBlockState().is(AACompat.gourdnut) && ((IBlockBehaviourAccessor)this).callCanSurvive(state, level, pos)) {
                    ripe = false;
                }
            }
            if (ripe) {
                level.scheduleTick(pos, (Block) (Object) this, this.gravitygourds$getDelayAfterPlace());
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tock(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (((Block)(Object)this).defaultBlockState().is(GGBlockTags.GOURDS) && FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            if(ABUNDANTATMOSPHERE && state.is(AACompat.gourdnut)){
                if (AACompat.isHanging(state)) {
                    state = AACompat.ground(state);
                }
            }
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, pos, state);
        }
    }
}
