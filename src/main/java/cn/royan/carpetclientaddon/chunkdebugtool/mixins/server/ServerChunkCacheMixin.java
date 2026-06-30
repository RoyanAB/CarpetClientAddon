package cn.royan.carpetclientaddon.chunkdebugtool.mixins.server;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.chunk.ServerChunkCache;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ServerChunkCache.class)
public class ServerChunkCacheMixin {
	@Shadow
	@Final
	private ServerWorld world;

	@Shadow
	@Final
	private Set<Long> chunksToUnload;

	@Inject(method = "unloadChunk", at = @At(value = "FIELD", target = "net/minecraft/server/world/chunk/ServerChunkCache.chunksToUnload : Ljava/util/Set;"))
	public void onReasonLogging1(WorldChunk chunk, CallbackInfo ci) {
		if (CarpetClientChunkLogger.logger.enabled) {
			CarpetClientChunkLogger.logger.log(world, chunk.chunkX, chunk.chunkZ, CarpetClientChunkLogger.Event.QUEUE_UNLOAD);
		}
	}

	@Inject(method = "getLoadedChunk", at = @At(value = "FIELD", target = "net/minecraft/world/chunk/WorldChunk.removed : Z"))
	public void onReasonLogging2(int chunkX, int chunkZ, CallbackInfoReturnable<WorldChunk> cir, @Local WorldChunk chunk) {
		if (CarpetClientChunkLogger.logger.enabled && chunk.removed) {
			CarpetClientChunkLogger.logger.log(world, chunkX, chunkZ, CarpetClientChunkLogger.Event.CANCEL_UNLOAD);
		}
	}

	@Inject(method = "getGeneratedChunk", at = @At(value = "FIELD", target = "net/minecraft/server/world/chunk/ServerChunkCache.chunks : Lit/unimi/dsi/fastutil/longs/Long2ObjectMap;"))
	public void onReasonLogging3(int chunkX, int chunkZ, CallbackInfoReturnable<WorldChunk> cir) {
		if (CarpetClientChunkLogger.logger.enabled) {
			CarpetClientChunkLogger.logger.log(world, chunkX, chunkZ, CarpetClientChunkLogger.Event.LOADING);
		}
	}

	@Inject(method = "getGeneratedChunk", at = @At(value = "INVOKE", target = "net/minecraft/world/chunk/WorldChunk.populate (Lnet/minecraft/world/chunk/ChunkSource;Lnet/minecraft/world/chunk/ChunkGenerator;)V"))
	public void onReasonLoggingStart4(int chunkX, int chunkZ, CallbackInfoReturnable<WorldChunk> cir) {
		if (CarpetClientChunkLogger.logger.enabled)
			CarpetClientChunkLogger.setReason("Population triggering neighbouring chunks to cancel unload");
	}

	@Inject(method = "getGeneratedChunk", at = @At(value = "INVOKE", target = "net/minecraft/world/chunk/WorldChunk.populate (Lnet/minecraft/world/chunk/ChunkSource;Lnet/minecraft/world/chunk/ChunkGenerator;)V", shift = At.Shift.AFTER))
	public void onReasonLoggingEnd4(int chunkX, int chunkZ, CallbackInfoReturnable<WorldChunk> cir) {
		CarpetClientChunkLogger.resetToOldReason();
	}

	@Inject(method = "getChunk", at = @At(value = "INVOKE", target = "net/minecraft/world/chunk/ChunkGenerator.generateChunk (II)Lnet/minecraft/world/chunk/WorldChunk;", shift = At.Shift.AFTER))
	public void onReasonLogging5(int chunkX, int chunkZ, CallbackInfoReturnable<WorldChunk> cir) {
		if (CarpetClientChunkLogger.logger.enabled)
			CarpetClientChunkLogger.logger.log(world, chunkX, chunkZ, CarpetClientChunkLogger.Event.GENERATING);
	}

	@Inject(method = "tick", at = @At(value = "FIELD", target = "net/minecraft/server/world/chunk/ServerChunkCache.chunksToUnload : Ljava/util/Set;", ordinal = 1))
	public void onReasonLoggingStart6(CallbackInfoReturnable<Boolean> cir) {
		if (CarpetClientChunkLogger.logger.enabled)
			CarpetClientChunkLogger.setReason("Unloading chunk and writing to disk");
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/server/world/chunk/ServerChunkCache.saveEntities (Lnet/minecraft/world/chunk/WorldChunk;)V", shift = At.Shift.AFTER))
	public void onReasonLogging7(CallbackInfoReturnable<Boolean> cir, @Local WorldChunk chunk) {
		if (CarpetClientChunkLogger.logger.enabled)
			CarpetClientChunkLogger.logger.log(world, chunk.chunkX, chunk.chunkZ, CarpetClientChunkLogger.Event.UNLOADING);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/world/chunk/storage/ChunkStorage.tick ()V"))
	public void onReasonLoggingEnd6(CallbackInfoReturnable<Boolean> cir) {
		if (!chunksToUnload.isEmpty()) {
			CarpetClientChunkLogger.resetReason();
		}
	}
}
