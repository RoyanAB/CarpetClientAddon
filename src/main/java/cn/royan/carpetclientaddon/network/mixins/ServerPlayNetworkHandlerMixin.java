package cn.royan.carpetclientaddon.network.mixins;

import cn.royan.carpetclientaddon.CarperClientAddon;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	public ServerPlayerEntity player;

	@Inject(method = "handleCustomPayload", at = @At(value = "RETURN"))
	private void handleCustomPayload(CustomPayloadC2SPacket packet, CallbackInfo ci) {
		CarperClientAddon.pluginChannels.process(this.player, packet);
	}
}
