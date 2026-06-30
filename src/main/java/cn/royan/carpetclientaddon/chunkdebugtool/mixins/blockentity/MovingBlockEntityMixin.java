package cn.royan.carpetclientaddon.chunkdebugtool.mixins.blockentity;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import net.minecraft.block.entity.MovingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MovingBlockEntity.class)
public class MovingBlockEntityMixin {
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/world/World.removeBlockEntity (Lnet/minecraft/util/math/BlockPos;)V"))
	public void onReasonLoggingStart(CallbackInfo ci) {
		if (CarpetClientChunkLogger.logger.enabled)
			CarpetClientChunkLogger.setReason("Piston block finishes moving");
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/world/World.removeBlockEntity (Lnet/minecraft/util/math/BlockPos;)V", shift = At.Shift.AFTER))
	public void onReasonLoggingEnd(CallbackInfo ci) {
		CarpetClientChunkLogger.resetReason();
	}
}
