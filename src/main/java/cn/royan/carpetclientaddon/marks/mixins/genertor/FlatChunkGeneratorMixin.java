package cn.royan.carpetclientaddon.marks.mixins.genertor;

import cn.royan.carpetclientaddon.marks.fakes.ChunkGeneratorInterface;
import cn.royan.carpetclientaddon.marks.fakes.StructureFeatureInterface;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.structure.StructureFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(FlatChunkGenerator.class)
public class FlatChunkGeneratorMixin implements ChunkGeneratorInterface {
	@Shadow
	@Final
	private Map<String, StructureFeature> structures;

	@Unique
	@Override
	public NbtList getBoundingBoxes(Entity entity) {
		NbtList nbttaglist = new NbtList();
		for (Map.Entry e : this.structures.entrySet()) {
			nbttaglist.addElement(((StructureFeatureInterface) e.getValue()).getBoundingBoxes(entity, 1));
		}
		return nbttaglist;
	}
}
