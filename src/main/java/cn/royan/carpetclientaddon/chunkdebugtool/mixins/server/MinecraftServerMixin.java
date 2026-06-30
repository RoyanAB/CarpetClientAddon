package cn.royan.carpetclientaddon.chunkdebugtool.mixins.server;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/server/MinecraftServer.saveWorlds (Z)V"))
	public void onReasonLoggingStart(CallbackInfo ci) {
		if (CarpetClientChunkLogger.logger.enabled)
			CarpetClientChunkLogger.setReason("Autosave queuing chunks for unloading");
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/server/MinecraftServer.saveWorlds (Z)V", shift = At.Shift.AFTER))
	public void onReasonLoggingEnd(CallbackInfo ci) {
		CarpetClientChunkLogger.resetReason();
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/util/profiler/Profiler.pop ()V", ordinal = 3, shift = At.Shift.AFTER))
	public void onReasonLoggingSend(CallbackInfo ci) {
		if (CarpetClientChunkLogger.logger.enabled) CarpetClientChunkLogger.logger.sendAll();
	}
}
