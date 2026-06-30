package cn.royan.carpetclientaddon;

import carpet.api.settings.Rule;
import cn.royan.carpetclientaddon.chunkdebugtool.utils.ChunkDebugToolModifier;

import static carpet.api.settings.RuleCategory.CREATIVE;

public class CarpetSettings {
	@Rule(desc = "Enables chunk debug on carpet client.", categories = CREATIVE, validators = {ChunkDebugToolModifier.class})
	public static boolean chunkDebugTool = false;

	@Rule(desc = "Enables randomtick indexing on carpet client.", categories = CREATIVE)
	public static boolean randomTickingChunkUpdates = false;
}
