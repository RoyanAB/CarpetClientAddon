package cn.royan.carpetclientaddon.chunkdebugtool.mixins.server;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkGenerator;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldChunk.class)
public class WorldChunkMixin {
	@Shadow
	@Final
	private World world;

	@Shadow
	@Final
	public int chunkX;

	@Shadow
	@Final
	public int chunkZ;

	@Inject(method = "populate(Lnet/minecraft/world/chunk/ChunkGenerator;)V", at = @At(value = "INVOKE", target = "net/minecraft/world/chunk/WorldChunk.markDirty ()V", ordinal = 0))
	public void onReasonLogging1(ChunkGenerator generator, CallbackInfo ci) {
		if (CarpetClientChunkLogger.logger.enabled) {
			CarpetClientChunkLogger.setReason("Generating structure");
			CarpetClientChunkLogger.logger.log(world, chunkX, chunkZ, CarpetClientChunkLogger.Event.GENERATING_STRUCTURES);
			CarpetClientChunkLogger.resetReason();
		}
	}

	@Inject(method = "populate(Lnet/minecraft/world/chunk/ChunkGenerator;)V", at = @At(value = "INVOKE", target = "net/minecraft/world/chunk/ChunkGenerator.populateChunk (II)V"))
	public void onReasonLogging2(ChunkGenerator generator, CallbackInfo ci) {
		if (CarpetClientChunkLogger.logger.enabled) {
			CarpetClientChunkLogger.setReason("Populating chunk");
			CarpetClientChunkLogger.logger.log(world, chunkX, chunkZ, CarpetClientChunkLogger.Event.POPULATING);
			CarpetClientChunkLogger.resetReason();
		}
	}
}
