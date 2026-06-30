package cn.royan.carpetclientaddon.network;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import cn.royan.carpetclientaddon.marks.CarpetClientMarkers;
import cn.royan.carpetclientaddon.network.util.PacketSplitter;
import cn.royan.carpetclientaddon.network.util.PluginChannelHandler;
import cn.royan.carpetclientaddon.others.CarpetClientRandomtickingIndexing;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;

import java.util.LinkedHashSet;

public class CarpetClientServer implements PluginChannelHandler {
	private static final LinkedHashSet<ServerPlayerEntity> players = new LinkedHashSet<>();
	public static final String CARPET_CHANNEL_NAME = "carpet:client";

	public String[] getChannels() {
		return new String[]{CARPET_CHANNEL_NAME};
	}

	public void onCustomPayload(CustomPayloadC2SPacket packet, ServerPlayerEntity player) {
		PacketByteBuf buffer = PacketSplitter.receive(player, packet);
		if (buffer != null) {
			CarpetClientMessageHandler.handler(player, buffer);
		}
	}

	public boolean register(String channel, ServerPlayerEntity sender) {
		players.add(sender);
		CarpetClientMessageHandler.sendAllGUIOptions(sender);
//        CarpetClientMessageHandler.sendCustomRecipes(sender);
		return true;
	}

	public void unregister(String channel, ServerPlayerEntity player) {
		players.remove(player);
		CarpetClientMarkers.unregisterPlayerVillageMarkers(player);
		CarpetClientChunkLogger.logger.unregisterPlayer(player);
		CarpetClientRandomtickingIndexing.unregisterPlayer(player);
	}

	static public LinkedHashSet<ServerPlayerEntity> getRegisteredPlayers() {
		return players;
	}

	public static boolean isPlayerRegistered(ServerPlayerEntity player) {
		return players.contains(player);
	}

	public static void sender(PacketByteBuf data) {
		for (ServerPlayerEntity player : CarpetClientServer.getRegisteredPlayers()) {
			data.retain();
			PacketSplitter.send(player, CARPET_CHANNEL_NAME, data);
		}
		data.release();
	}

	public static void sender(PacketByteBuf data, ServerPlayerEntity player) {
		PacketSplitter.send(player, CARPET_CHANNEL_NAME, data);
	}
}
