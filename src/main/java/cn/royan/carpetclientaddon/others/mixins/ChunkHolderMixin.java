package cn.royan.carpetclientaddon.others.mixins;

import cn.royan.carpetclientaddon.others.CarpetClientRandomtickingIndexing;
import net.minecraft.server.ChunkHolder;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkHolder.class)
public class ChunkHolderMixin {
	@Inject(method = "addPlayer", at = @At(value = "RETURN"))
	public void onAddPlayer(ServerPlayerEntity player, CallbackInfo ci) {
		CarpetClientRandomtickingIndexing.enableUpdate(player);
	}

	@Inject(method = "removePlayer", at = @At(value = "RETURN"))
	public void onRemovePlayer(ServerPlayerEntity player, CallbackInfo ci) {
		CarpetClientRandomtickingIndexing.enableUpdate(player);
	}
}
