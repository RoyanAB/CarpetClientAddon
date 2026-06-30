package cn.royan.carpetclientaddon.marks.fakes;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtList;

public interface ChunkGeneratorInterface {
	default NbtList getBoundingBoxes(Entity entity) {
		return null;
	}
}
