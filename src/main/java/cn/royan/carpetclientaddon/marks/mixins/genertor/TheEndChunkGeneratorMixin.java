package cn.royan.carpetclientaddon.marks.mixins.genertor;

import cn.royan.carpetclientaddon.marks.CarpetClientMarkers;
import cn.royan.carpetclientaddon.marks.fakes.ChunkGeneratorInterface;
import cn.royan.carpetclientaddon.marks.fakes.StructureFeatureInterface;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.gen.chunk.TheEndChunkGenerator;
import net.minecraft.world.gen.structure.EndCityStructure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TheEndChunkGenerator.class)
public class TheEndChunkGeneratorMixin implements ChunkGeneratorInterface {
	@Shadow
	@Final
	private EndCityStructure endCity;

	@Unique
	@Override
	public NbtList getBoundingBoxes(Entity entity) {
		NbtList nbttaglist = new NbtList();
		nbttaglist.addElement(((StructureFeatureInterface) this.endCity).getBoundingBoxes(entity, CarpetClientMarkers.END_CITY));
		return nbttaglist;
	}
}
