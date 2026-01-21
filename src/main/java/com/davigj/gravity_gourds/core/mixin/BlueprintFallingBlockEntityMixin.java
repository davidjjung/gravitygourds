package com.davigj.gravity_gourds.core.mixin;

import com.davigj.gravity_gourds.core.other.FallingBlockUtil;
import com.davigj.gravity_gourds.core.other.GGBlockTags;
import com.davigj.gravity_gourds.core.other.compat.AtmosphericCompat;
import com.davigj.gravity_gourds.core.registry.GGSoundEvents;
import com.teamabnormals.blueprint.common.entity.BlueprintFallingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static com.davigj.gravity_gourds.core.other.compat.GGModConstants.ATMOSPHERIC;

@Pseudo
@Mixin(BlueprintFallingBlockEntity.class)
public class BlueprintFallingBlockEntityMixin extends FallingBlockEntity {
    public BlueprintFallingBlockEntityMixin(EntityType<? extends FallingBlockEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/teamabnormals/blueprint/common/entity/BlueprintFallingBlockEntity;callOnBrokenAfterFall(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/core/BlockPos;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void slice(CallbackInfo ci, Block block, BlockPos pos) {
        Level level = this.level();
        if (!(level instanceof ServerLevel server)) return;
        if (level.getBlockState(pos).is(GGBlockTags.POINTY) && block.defaultBlockState().is(GGBlockTags.GOURDS)) {
            if (ATMOSPHERIC)
                if (block == AtmosphericCompat.yuccaBundle) {
                    FallingBlockUtil.fx(server, pos, GGSoundEvents.GOURD_SQUISH.get(), AtmosphericCompat.yuccaFruit, 0.7F, this);
                } else if (block == AtmosphericCompat.roastedYuccaBundle) {
                    FallingBlockUtil.fx(server, pos, SoundEvents.WOOD_BREAK, AtmosphericCompat.roastedYuccaFruit, 1.2F, this);
                }
        }
    }
}
