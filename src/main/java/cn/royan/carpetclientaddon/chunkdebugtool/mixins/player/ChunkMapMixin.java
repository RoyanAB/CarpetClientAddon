package cn.royan.carpetclientaddon.chunkdebugtool.mixins.player;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import cn.royan.carpetclientaddon.chunkdebugtool.fakes.ChunkMapInterface;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import net.minecraft.server.ChunkHolder;
import net.minecraft.server.ChunkMap;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(ChunkMap.class)
public class ChunkMapMixin implements ChunkMapInterface {
	@Shadow
	private final List<ChunkHolder> loading = Lists.newLinkedList();
	@Shadow
	private final List<ChunkHolder> chunks = Lists.newArrayList();

	@Unique
	@Override
	public Iterator<ChunkPos> carpetGetAllChunkCoordinates() {
		return new AbstractIterator<ChunkPos>() {
			final Iterator<ChunkHolder> allChunks = Iterators.concat(chunks.iterator(), loading.iterator());

			@Override
			protected ChunkPos computeNext() {
				if (allChunks.hasNext()) {
					return allChunks.next().getPos();
				} else {
					return this.endOfData();
				}
			}
		};
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/server/world/chunk/ServerChunkCache.unloadAllChunks ()V"))
	public void onReasonLoggingStart(CallbackInfo ci) {
		if (CarpetClientChunkLogger.logger.enabled)
			CarpetClientChunkLogger.setReason("Dimensional unloading due to no players");
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/server/world/chunk/ServerChunkCache.unloadAllChunks ()V", shift = At.Shift.AFTER))
	public void onReasonLoggingEnded(CallbackInfo ci) {
		CarpetClientChunkLogger.resetReason();
	}

	@Inject(method = "unload", at = @At(value = "INVOKE", target = "net/minecraft/server/world/chunk/ServerChunkCache.unloadChunk (Lnet/minecraft/world/chunk/WorldChunk;)V"))
	public void onReasonLoggingStart2(ChunkHolder chunk, CallbackInfo ci) {
		if (CarpetClientChunkLogger.logger.enabled)
			CarpetClientChunkLogger.setReason("Player leaving chunk, queuing unload");
	}

	@Inject(method = "unload", at = @At(value = "INVOKE", target = "net/minecraft/server/world/chunk/ServerChunkCache.unloadChunk (Lnet/minecraft/world/chunk/WorldChunk;)V", shift = At.Shift.AFTER))
	public void onReasonLoggingEnded2(ChunkHolder chunk, CallbackInfo ci) {
		CarpetClientChunkLogger.resetReason();
	}
}
