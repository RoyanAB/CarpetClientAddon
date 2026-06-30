package cn.royan.carpetclientaddon.network.mixins;

import cn.royan.carpetclientaddon.CarperClientAddon;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	@Inject(method = "add", at = @At(value = "TAIL"))
	private void handleLogin(ServerPlayerEntity player, CallbackInfo ci) {
		CarperClientAddon.pluginChannels.onPlayerConnected(player);
	}

	@Inject(method = "remove", at = @At(value = "HEAD"))
	private void handleDisconnect(ServerPlayerEntity player, CallbackInfo ci) {
		CarperClientAddon.pluginChannels.onPlayerDisconnected(player);
	}
}
