package cn.royan.carpetclientaddon.network;

import carpet.CarpetServer;
import carpet.SharedConstants;
import carpet.api.settings.CarpetRule;
import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import cn.royan.carpetclientaddon.marks.CarpetClientMarkers;
import cn.royan.carpetclientaddon.others.CarpetClientRandomtickingIndexing;
import cn.royan.carpetclientaddon.rulesender.CarpetClientRuleChanger;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;

public class CarpetClientMessageHandler {
	// Main packet data names
	public static final int GUI_ALL_DATA = 0;
	public static final int RULE_REQUEST = 1;
	public static final int VILLAGE_MARKERS = 2;
	public static final int BOUNDINGBOX_MARKERS = 3;
	public static final int TICKRATE_CHANGES = 4;
	public static final int CHUNK_LOGGER = 5;
	public static final int PISTON_UPDATES = 6;
	public static final int RANDOMTICK_DISPLAY = 7;
	public static final int CUSTOM_RECIPES = 8;

	private static final int NET_VERSION = 1;

	public static void handler(ServerPlayerEntity sender, PacketByteBuf data) {
		int type = data.readInt();

		if (GUI_ALL_DATA == type) {
			sendAllGUIOptions(sender);
		} else if (RULE_REQUEST == type) {
			ruleRequest(sender, data);
		} else if (VILLAGE_MARKERS == type) {
			registerVillagerMarkers(sender, data);
		} else if (BOUNDINGBOX_MARKERS == type) {
			boundingboxRequest(sender, data);
		} else if (CHUNK_LOGGER == type) {
			CarpetClientChunkLogger.logger.registerPlayer(sender, data);
		} else if (RANDOMTICK_DISPLAY == type) {
			CarpetClientRandomtickingIndexing.register(sender, data);
		}
//        else if (CUSTOM_RECIPES == type) {
//            confirmationReceivedCustomRecipesSendUpdate(sender);
//        }
	}

	private static void registerVillagerMarkers(ServerPlayerEntity sender, PacketByteBuf data) {
		CarpetClientMarkers.registerVillagerMarkers(sender, data);
	}

	private static void boundingboxRequest(ServerPlayerEntity sender, PacketByteBuf data) {
		CarpetClientMarkers.updateClientBoundingBoxMarkers(sender, data);
	}

	private static void ruleRequest(ServerPlayerEntity sender, PacketByteBuf data) {
		CarpetClientRuleChanger.ruleChanger(sender, data);
	}

	public static void sendAllGUIOptions(ServerPlayerEntity sender) {
		PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
		data.writeInt(GUI_ALL_DATA);

		NbtCompound chunkData = new NbtCompound();

		chunkData.putString("carpetVersion", SharedConstants.carpetVersion);
		chunkData.putFloat("tickrate", 20.0F);
		chunkData.putInt("netVersion", NET_VERSION);
		NbtList listNBT = new NbtList();
		for (CarpetRule<?> rule : CarpetServer.settingsManager.getCarpetRules()) {
			Class<?> type = rule.type();

			boolean isfloat = type == double.class || type == float.class || type == Double.class || type == Float.class;

			NbtCompound ruleNBT = new NbtCompound();
			ruleNBT.putString("rule", rule.name());
			ruleNBT.putString("current", rule.value().toString());
			ruleNBT.putString("default", rule.defaultValue().toString());
			ruleNBT.putBoolean("isfloat", isfloat);
			listNBT.addElement(ruleNBT);
		}
		chunkData.put("ruleList", listNBT);

		try {
			data.writeNbtCompound(chunkData);
		} catch (Exception e) {
		}

		CarpetClientServer.sender(data, sender);
	}

	public static void sendNBTVillageData(ServerPlayerEntity sender, NbtCompound compound) {
		PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
		data.writeInt(CarpetClientMessageHandler.VILLAGE_MARKERS);

		data.writeNbtCompound(compound);

		CarpetClientServer.sender(data, sender);
	}

	public static void sendNBTBoundingboxData(ServerPlayerEntity sender, NbtCompound compound) {
		PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
		data.writeInt(CarpetClientMessageHandler.BOUNDINGBOX_MARKERS);

		data.writeNbtCompound(compound);

		CarpetClientServer.sender(data, sender);
	}

//    public static void sendTickRateChanges(CommandSource sender) {
//        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
//        data.writeInt(CarpetClientMessageHandler.TICKRATE_CHANGES);
//		if(CarperClientAddon.hasSubtick)
//			data.writeFloat(((ITickHandleable) sender.getServer()).tickHandler().serverTickRateManager.tickrate());
//		else
//			data.writeFloat(((MinecraftServerF) sender.getServer()).getTickRateManager().tickrate());
//
//        CarpetClientServer.sender(data);
//    }
//
//	public static float getTickRate(CommandSource sender) {
//		if(CarperClientAddon.hasSubtick)
//			return  ((ITickHandleable) sender.getServer()).tickHandler().serverTickRateManager.tickrate();
//		else
//			return  ((MinecraftServerF) sender.getServer()).getTickRateManager().tickrate();
//	}

	public static void sendNBTChunkData(ServerPlayerEntity sender, int dataType, NbtCompound compound) {
		PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
		data.writeInt(CarpetClientMessageHandler.CHUNK_LOGGER);
		data.writeInt(dataType);
		try {
			data.writeNbtCompound(compound);
		} catch (Exception e) {
		}
		CarpetClientServer.sender(data, sender);
	}

//    public static void sendPistonUpdate() {
//        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
//        data.writeInt(CarpetClientMessageHandler.PISTON_UPDATES);
//
//        CarpetClientServer.sender(data);
//    }

	public static void sendNBTRandomTickData(ServerPlayerEntity sender, NbtCompound compound) {
		PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
		data.writeInt(CarpetClientMessageHandler.RANDOMTICK_DISPLAY);
		try {
			data.writeNbtCompound(compound);
		} catch (Exception e) {
		}
		CarpetClientServer.sender(data, sender);
	}
//
//    public static void sendCustomRecipes(EntityPlayerMP sender) {
//        if (CustomCrafting.getRecipeList().size() == 0) return;
//        PacketBuffer data = new PacketBuffer(Unpooled.buffer());
//        data.writeInt(CUSTOM_RECIPES);
//
//        NBTTagCompound chunkData = new NBTTagCompound();
//
//        NBTTagList listNBT = new NBTTagList();
//        for (Pair<String, JsonObject> pair : CustomCrafting.getRecipeList()) {
//            NBTTagCompound recipe = new NBTTagCompound();
//            recipe.setString("name", pair.getKey());
//            recipe.setString("recipe", pair.getValue().toString());
//            listNBT.appendTag(recipe);
//        }
//        chunkData.setTag("recipeList", listNBT);
//
//        try {
//            data.writeCompoundTag(chunkData);
//        } catch (Exception e) {
//        }
//
//        CarpetClientServer.sender(data, sender);
//    }
//
//    public static void confirmationReceivedCustomRecipesSendUpdate(EntityPlayerMP sender) {
//        sender.getRecipeBook().init(sender);
//    }
}
