package com.davigj.gravity_gourds.core.mixin;

import com.davigj.gravity_gourds.core.GGConfig;
import com.davigj.gravity_gourds.core.other.GGBlockTags;
import com.davigj.gravity_gourds.core.other.GGConstants;
import com.davigj.gravity_gourds.core.registry.GGSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static net.minecraft.world.level.block.Block.dropResources;

@Mixin(targets = "com.teamabnormals.blueprint.common.entity.BlueprintFallingBlockEntity")
public abstract class BlueprintFallingBlockEntityMixin extends FallingBlockEntity {
    @Unique
    private static final float SQUISH_PITCH = 0.5F;
    @Unique
    private static final float SQUISH_VOL = 1.0F;
    @Unique
    private static final double PARTICLE_SPEED = 0.05D;

    public BlueprintFallingBlockEntityMixin(EntityType<? extends FallingBlockEntity> p_31950_, Level p_31951_) {
        super(p_31950_, p_31951_);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/teamabnormals/blueprint/common/entity/BlueprintFallingBlockEntity;callOnBrokenAfterFall(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/core/BlockPos;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void slice(CallbackInfo ci, Block block, BlockPos pos) {
        Level level = this.getLevel();
        if (!(level instanceof ServerLevel server)) return;
        if (level.getBlockState(pos).is(GGBlockTags.POINTY)) {
            if (this.getBlockState().is(GGConstants.yuccaBundle) || this.getBlockState().is(GGConstants.roastedYuccaBundle)) {
                gravitygourds$handleYuccaBundle(server, pos);
                dropResources(block.defaultBlockState(), level, pos.above(), null);
                ci.cancel();
            }
        }
    }

    @Unique
    private void gravitygourds$handleYuccaBundle(ServerLevel server, BlockPos pos) {
        level.playSound(null, pos, GGSoundEvents.GOURD_SQUISH.get(), SoundSource.BLOCKS, SQUISH_VOL, SQUISH_PITCH);
        BlockParticleOption yuccaJuice = new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockState()).setPos(pos);
        server.sendParticles(yuccaJuice, this.getX(), this.getY(), this.getZ(), 8, 0, PARTICLE_SPEED, 0, 0.15D);
    }

    @Inject(method = "spawnDrops", at = @At("HEAD"), remap = false, cancellable = true)
    private void noDrops(CallbackInfo ci) {
        if ((this.getBlockState().is(GGConstants.yuccaBundle) || this.getBlockState().is(GGConstants.roastedYuccaBundle)) &&
                !level.getBlockState(this.blockPosition()).is(GGBlockTags.POINTY) && GGConfig.COMMON.yuccaDrop.get()) {
            this.spawnAtLocation(this.getBlockState().getBlock());
            ci.cancel();
        }
    }
}
