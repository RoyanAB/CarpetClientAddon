package cn.royan.carpetclientaddon.others.mixins;

import cn.royan.carpetclientaddon.others.CarpetClientRandomtickingIndexing;
import net.minecraft.server.ChunkMap;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkMap.class)
public class ChunkMapMixin {

	@Shadow
	@Final
	private ServerWorld world;

	@Inject(method = "tick", at = @At(value = "RETURN"))
	public void onTick(CallbackInfo ci) {
		if (CarpetClientRandomtickingIndexing.sendUpdates(this.world)) {
			CarpetClientRandomtickingIndexing.sendRandomtickingChunkOrder(this.world, (ChunkMap) (Object) this);
		}
	}
}
