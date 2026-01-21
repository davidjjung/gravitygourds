package com.davigj.gravity_gourds.core.other;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class FallingBlockUtil {
    private static final int PARTICLE_COUNT = 28;
    private static final double PARTICLE_OFFSET = 0.25D;
    private static final float SQUISH_VOL = 1.0F;
    private static final double PARTICLE_SPEED = 0.1D;

    public static void fx(ServerLevel server, BlockPos pos, SoundEvent sound, ItemStack stack, float pitch, Entity entity) {
        entity.level().playSound(null, pos, sound, SoundSource.BLOCKS, (float) SQUISH_VOL, pitch);
        server.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), entity.getX(), entity.getY(), entity.getZ(),
                PARTICLE_COUNT, PARTICLE_OFFSET * 0.5F, PARTICLE_OFFSET, PARTICLE_OFFSET * 0.5F, PARTICLE_SPEED);
    }
}
