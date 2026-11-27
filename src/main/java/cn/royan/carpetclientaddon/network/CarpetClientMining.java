package cn.royan.carpetclientaddon.network;

import cn.royan.carpetclientaddon.network.util.PacketSplitter;
import cn.royan.carpetclientaddon.network.util.PluginChannelHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

// Carpet Client sends start/stop mining on this channel instead of the vanilla packet
// after the server sends netVersion >= 1. Port from carpetmod112 `NetHandlerPlayServer`.
public class CarpetClientMining implements PluginChannelHandler {
	public static final String MINE_CHANNEL_NAME = "carpet:mine";

	public String[] getChannels() {
		return new String[]{MINE_CHANNEL_NAME};
	}

	public void onCustomPayload(CustomPayloadC2SPacket packet, ServerPlayerEntity player) {
		PacketByteBuf payload = PacketSplitter.receive(player, packet);
		if (payload == null) return;

		boolean start = payload.readBoolean();
		BlockPos pos = payload.readBlockPos();
		Direction face = Direction.byId(payload.readUnsignedByte());
		// ornithe-carpet doesn't use these
		boolean instaMine = payload.readBoolean();
		boolean carefulBreak = payload.readBoolean();

		ServerWorld world = player.getServerWorld();
		if (start) {
			if (!player.server.isSpawnProtected(world, pos, player) && world.getWorldBorder().contains(pos)) {
				player.interactionManager.startMiningBlock(pos, face);
			} else {
				player.networkHandler.sendPacket(new BlockUpdateS2CPacket(world, pos));
			}
		} else {
			player.interactionManager.finishMiningBlock(pos);
		}
	}
}

