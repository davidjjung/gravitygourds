package com.davigj.gravity_gourds.core.mixin;

import com.davigj.gravity_gourds.core.GGConfig;
import com.davigj.gravity_gourds.core.other.GGBlockTags;
import com.davigj.gravity_gourds.core.other.GGConstants;
import com.davigj.gravity_gourds.core.registry.GGSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.time.LocalDate;

import static net.minecraft.world.level.block.Block.dropResources;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    @Shadow private BlockState blockState;
    @Unique
    private static final int MELON_PARTICLE_COUNT = 28;
    @Unique
    private static final int SMOKE_PARTICLE_COUNT = 3;
    @Unique
    private static final int SEED_PARTICLE_COUNT = 20;
    @Unique
    private static final double PARTICLE_SPEED = 0.05D;
    @Unique
    private static final double PARTICLE_OFFSET = 0.25D;
    @Unique
    private static final float SQUISH_PITCH = 0.5F;
    @Unique
    private static final float SQUISH_VOL = 1.0F;

    public FallingBlockEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;callOnBrokenAfterFall(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/core/BlockPos;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void slice(CallbackInfo ci, Block block, BlockPos pos) {
        Level level = this.level();
        if (!(level instanceof ServerLevel server)) return;
        if (level.getBlockState(pos).is(GGBlockTags.POINTY)) {
            if (block instanceof MelonBlock) {
                gravitygourds$handleMelonBlock(server, pos);
                dropResources(block.defaultBlockState(), level, pos.above(), null);
                ci.cancel();
            } else if (block instanceof PumpkinBlock || this.blockState.is(GGConstants.largePumpkinSlice)) {
                gravitygourds$handlePumpkinBlock(server, pos, ci);
            }
        }
    }

    @Unique
    private void gravitygourds$handleMelonBlock(ServerLevel server, BlockPos pos) {
        level().playSound(null, pos, GGSoundEvents.GOURD_SQUISH.get(), SoundSource.BLOCKS, SQUISH_VOL, SQUISH_PITCH);
        BlockParticleOption chunks = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.RED_CONCRETE.defaultBlockState()).setPos(pos);
        BlockParticleOption melon = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.MELON.defaultBlockState()).setPos(pos);
        server.sendParticles(chunks, this.getX(), this.getY(), this.getZ(), MELON_PARTICLE_COUNT, 0, PARTICLE_SPEED, 0, PARTICLE_OFFSET);
        server.sendParticles(melon, this.getX(), this.getY(), this.getZ(), 8, 0, PARTICLE_SPEED, 0, 0.15D);
    }

    @Unique
    private void gravitygourds$handlePumpkinBlock(ServerLevel server, BlockPos pos, CallbackInfo ci) {
        LocalDate localdate = LocalDate.now();
        int day = localdate.getDayOfMonth();
        int month = localdate.getMonth().getValue();
        if (gravitygourds$isHalloween(day, month)) {
            level().playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
            gravitygourds$handleHalloweenPumpkin(server, pos, ci);
        } else if (ModList.get().isLoaded("farmersdelight")) {
            level().playSound(null, pos, GGSoundEvents.GOURD_SQUISH.get(), SoundSource.BLOCKS, SQUISH_VOL, SQUISH_PITCH);
            gravitygourds$handleFarmerDelightPumpkin(server, pos, ci);
        }
    }

    @Unique
    private boolean gravitygourds$isHalloween(int day, int month) {
        return month == 10 && day == 31 && this.level().getRandom().nextFloat() < 0.35F;
    }

    @Unique
    private void gravitygourds$handleHalloweenPumpkin(ServerLevel server, BlockPos pos, CallbackInfo ci) {
        ItemStack stack = new ItemStack(Items.CARVED_PUMPKIN);
        ItemStack seeds = new ItemStack(Items.PUMPKIN_SEEDS, 3);
        spawnAtLocation(stack);
        spawnAtLocation(seeds);
        ItemParticleOption seedBits = new ItemParticleOption(ParticleTypes.ITEM, seeds);
        server.sendParticles(seedBits, this.getX(), this.getY(), this.getZ(), SEED_PARTICLE_COUNT, 0, PARTICLE_SPEED, 0, 0.12D);
        server.sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), SMOKE_PARTICLE_COUNT, 0, PARTICLE_SPEED, 0, 0.03D);
        ci.cancel();
    }

    @Unique
    private void gravitygourds$handleFarmerDelightPumpkin(ServerLevel server, BlockPos pos, CallbackInfo ci) {
        ItemStack stack = GGConstants.pumpkinSlice;
        if (stack != null) {
            stack.setCount(GGConfig.COMMON.sliceNumber.get());
            if (this.level().getRandom().nextDouble() < GGConfig.COMMON.bonusChance.get()) {
                stack.setCount(stack.getCount() + GGConfig.COMMON.bonusNumber.get());
            }
            spawnAtLocation(stack);
            ci.cancel();
        }
        BlockParticleOption chunks = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.PUMPKIN.defaultBlockState()).setPos(pos);
        server.sendParticles(chunks, this.getX(), this.getY(), this.getZ(), MELON_PARTICLE_COUNT, 0, PARTICLE_SPEED, 0, PARTICLE_OFFSET);
    }
}
