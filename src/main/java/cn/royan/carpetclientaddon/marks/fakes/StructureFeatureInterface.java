package cn.royan.carpetclientaddon.marks.fakes;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtList;

public interface StructureFeatureInterface {
	NbtList getBoundingBoxes(Entity entity, int type);
}
