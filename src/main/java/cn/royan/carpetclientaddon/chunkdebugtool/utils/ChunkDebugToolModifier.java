package cn.royan.carpetclientaddon.chunkdebugtool.utils;

import carpet.api.settings.Validators;
import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;

public class ChunkDebugToolModifier extends Validators.SideEffectValidator<Boolean> {
	public Boolean parseValue(Boolean newValue) {
		return newValue;
	}

	public void performEffect(Boolean newValue) {

		if (!newValue) {
			CarpetClientChunkLogger.logger.disable();
		}
	}
}
