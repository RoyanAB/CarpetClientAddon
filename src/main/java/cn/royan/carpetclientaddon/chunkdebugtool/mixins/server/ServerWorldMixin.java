package cn.royan.carpetclientaddon.chunkdebugtool.mixins.server;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.world.BlockEvent;
import net.minecraft.server.world.ScheduledTick;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	@Inject(method = "doScheduledTicks", at = @At(value = "INVOKE", target = "net/minecraft/server/world/ServerWorld.getBlockState (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/BlockState;"))
	public void onReasonLoggingStart(boolean flush, CallbackInfoReturnable<Boolean> cir, @Local ScheduledTick scheduledTick) {
		if (CarpetClientChunkLogger.logger.enabled)
			CarpetClientChunkLogger.setReason("Block update: " + scheduledTick.getBlock().getTranslationKey());
	}

	@Inject(method = "doScheduledTicks", at = @At(value = "INVOKE", target = "net/minecraft/util/profiler/Profiler.pop ()V"))
	public void onReasonLoggingEnd(boolean flush, CallbackInfoReturnable<Boolean> cir) {
		CarpetClientChunkLogger.resetReason();
	}

	@Inject(method = "doBlockEvents", at = @At(value = "INVOKE", target = "net/minecraft/server/world/ServerWorld.doBlockEvent (Lnet/minecraft/server/world/BlockEvent;)Z"))
	public void onReasonLoggingStart2(CallbackInfo ci, @Local BlockEvent blockevent) {
		if (CarpetClientChunkLogger.logger.enabled)
			CarpetClientChunkLogger.setReason("Queued block event: " + blockevent);
	}

	@Inject(method = "doBlockEvents", at = @At(value = "INVOKE", target = "net/minecraft/server/world/ServerWorld$BlockEventQueue.clear ()V"))
	public void onReasonLoggingEnd2(CallbackInfo ci) {
		CarpetClientChunkLogger.resetReason();
	}
}
