package cn.royan.carpetclientaddon.chunkdebugtool.mixins.client;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/integrated/IntegratedServer;saveWorlds(Z)V"))
	public void onSaveWorldsLogging(CallbackInfo ci) {
		if (CarpetClientChunkLogger.logger.enabled) {
			CarpetClientChunkLogger.setReason("Unloading chunk and writing to disk (Game Pause)");
		}
	}
}
