package cn.royan.carpetclientaddon.marks;

import cn.royan.carpetclientaddon.marks.fakes.ChunkGeneratorInterface;
import cn.royan.carpetclientaddon.network.CarpetClientMessageHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.village.Village;

import java.util.ArrayList;

public class CarpetClientMarkers {

	public static final int OUTER_BOUNDING_BOX = 0;
	public static final int END_CITY = 1;
	public static final int FORTRESS = 2;
	public static final int TEMPLE = 3;
	public static final int VILLAGE = 4;
	public static final int STRONGHOLD = 5;
	public static final int MINESHAFT = 6;
	public static final int MONUMENT = 7;
	public static final int MANSION = 8;

	private static final ArrayList<ServerPlayerEntity> playersVillageMarkers = new ArrayList<>();

	public static void updateClientVillageMarkers(World worldObj) {
		if (playersVillageMarkers.isEmpty()) {
			return;
		}
		NbtList nbttaglist = new NbtList();
		NbtCompound tagCompound = new NbtCompound();

		for (Village village : worldObj.getVillages().getVillages()) {
			NbtCompound nbttagcompound = new NbtCompound();
			village.writeNbt(nbttagcompound);
			nbttaglist.addElement(nbttagcompound);
		}

		tagCompound.put("Villages", nbttaglist);

		for (ServerPlayerEntity sender : playersVillageMarkers) {
			CarpetClientMessageHandler.sendNBTVillageData(sender, tagCompound);
		}
	}

	public static void updateClientBoundingBoxMarkers(ServerPlayerEntity sender, PacketByteBuf data) {
		MinecraftServer ms = sender.world.getServer();
		ServerWorld ws = ms.getWorld(sender.dimension);
		NbtList list = ((ChunkGeneratorInterface) ws.getChunkSource().generator).getBoundingBoxes(sender);

		NbtCompound nbttagcompound = new NbtCompound();

		nbttagcompound.put("Boxes", list);
		nbttagcompound.putInt("Dimention", sender.dimension);
		nbttagcompound.putLong("Seed", sender.world.getSeed());

		CarpetClientMessageHandler.sendNBTBoundingboxData(sender, nbttagcompound);
	}

	public static void registerVillagerMarkers(ServerPlayerEntity sender, PacketByteBuf data) {
		boolean addPlayer = data.readBoolean();
		if (addPlayer) {
			playersVillageMarkers.add(sender);
			updateClientVillageMarkers(sender.world);
		} else {
			playersVillageMarkers.remove(sender);
		}
	}

	public static void unregisterPlayerVillageMarkers(ServerPlayerEntity player) {
		playersVillageMarkers.remove(player);
	}
}
