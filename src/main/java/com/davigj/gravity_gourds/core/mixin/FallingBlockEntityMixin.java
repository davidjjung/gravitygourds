package com.davigj.gravity_gourds.core.mixin;

import com.davigj.gravity_gourds.core.GGConfig;
import com.davigj.gravity_gourds.core.other.FallingBlockUtil;
import com.davigj.gravity_gourds.core.other.GGBlockTags;
import com.davigj.gravity_gourds.core.other.compat.AACompat;
import com.davigj.gravity_gourds.core.other.compat.AutumnityCompat;
import com.davigj.gravity_gourds.core.other.compat.FDCompat;
import com.davigj.gravity_gourds.core.registry.GGSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.time.LocalDate;

import static com.davigj.gravity_gourds.core.other.compat.GGModConstants.*;
import static net.minecraft.world.level.block.Block.dropResources;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    @Shadow
    private BlockState blockState;

    @Shadow public abstract void setHurtsEntities(float fallDamagePerDistance, int fallDamageMax);

    @Unique
    private static final int SMOKE_PARTICLE_COUNT = 3;
    @Unique
    private static final float SQUISH_PITCH = 0.5F;

    public FallingBlockEntityMixin(EntityType<?> p_19870_, Level p_19871_, boolean hurtEntities) {
        super(p_19870_, p_19871_);
    }

    private FallingBlockEntityMixin(Level p_31953_, double p_31954_, double p_31955_, double p_31956_, BlockState p_31957_, boolean hurtEntities) {
        this(EntityType.FALLING_BLOCK, p_31953_, hurtEntities);
        this.blockState = p_31957_;
    }

    @Inject(method = "tick", at = @At(value  = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;applyGravity()V"),
    locals = LocalCapture.CAPTURE_FAILHARD)
    private void bonk(CallbackInfo ci, Block block) {
        if (block.defaultBlockState().is(GGBlockTags.GOURDS) && GGConfig.COMMON.hurtEntities.get()) {
            this.setHurtsEntities(0.5F, 6);
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;callOnBrokenAfterFall(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/core/BlockPos;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void slice(CallbackInfo ci, Block block, BlockPos pos) {
        Level level = this.level();
        if (!(level instanceof ServerLevel server)) return;
        if (level.getBlockState(pos).is(GGBlockTags.POINTY) && block.defaultBlockState().is(GGBlockTags.GOURDS)) {
            if (block == Blocks.MELON) {
                FallingBlockUtil.fx(server, pos, GGSoundEvents.GOURD_SQUISH.get(), Items.MELON_SLICE.getDefaultInstance(), SQUISH_PITCH, this);
                dropResources(block.defaultBlockState(), level, pos.above(), null);
                ci.cancel();
            } else if (block instanceof PumpkinBlock || (AUTUMNITY && this.blockState.is(AutumnityCompat.largePumpkinSlice))) {
                gravitygourds$handlePumpkinBlock(server, pos, ci);
            } else if (ABUNDANTATMOSPHERE && block == AACompat.gourdnut) {
                gravitygourds$handleGourdnut(server, pos, ci);
            }
        }
    }

    @Unique
    private void gravitygourds$handlePumpkinBlock(ServerLevel server, BlockPos pos, CallbackInfo ci) {
        LocalDate localdate = LocalDate.now();
        int day = localdate.getDayOfMonth();
        int month = localdate.getMonth().getValue();
        if (gravitygourds$isHalloween(day, month)) {
            gravitygourds$handleHalloweenPumpkin(server, pos, ci);
        } else if (FARMERSDELIGHT) {
            gravitygourds$handleFarmerDelightPumpkin(server, pos, ci);
        }
    }

    @Unique
    private void gravitygourds$handleGourdnut(ServerLevel server, BlockPos pos, CallbackInfo ci) {
        if (FARMERSDELIGHT) {
            ItemStack stack = FDCompat.pumpkinSlice;
            if (stack != null) {
                stack.setCount(2);
                spawnAtLocation(stack);
                FallingBlockUtil.fx(server, pos, GGSoundEvents.GOURD_SQUISH.get(), new ItemStack(AACompat.gourdnut.asItem()), SQUISH_PITCH, this);
                ci.cancel();
            }
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
        FallingBlockUtil.fx(server, pos, SoundEvents.PUMPKIN_CARVE, seeds, 1.0F, this);
        server.sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), SMOKE_PARTICLE_COUNT, 0, 0.1D, 0, 0.03D);
        ci.cancel();
    }

    @Unique
    private void gravitygourds$handleFarmerDelightPumpkin(ServerLevel server, BlockPos pos, CallbackInfo ci) {
        ItemStack stack = FDCompat.pumpkinSlice;
        if (stack != null) {
            stack.setCount(GGConfig.COMMON.sliceNumber.get());
            if (this.level().getRandom().nextDouble() < GGConfig.COMMON.bonusChance.get()) {
                stack.setCount(stack.getCount() + GGConfig.COMMON.bonusNumber.get());
            }
            spawnAtLocation(stack);
            FallingBlockUtil.fx(server, pos, GGSoundEvents.GOURD_SQUISH.get(), stack, SQUISH_PITCH, this);
            ci.cancel();
        }
    }
}
