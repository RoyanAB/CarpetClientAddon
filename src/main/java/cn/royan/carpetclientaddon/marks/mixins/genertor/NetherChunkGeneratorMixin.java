package cn.royan.carpetclientaddon.marks.mixins.genertor;

import cn.royan.carpetclientaddon.marks.CarpetClientMarkers;
import cn.royan.carpetclientaddon.marks.fakes.ChunkGeneratorInterface;
import cn.royan.carpetclientaddon.marks.fakes.StructureFeatureInterface;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.gen.chunk.NetherChunkGenerator;
import net.minecraft.world.gen.structure.FortressStructure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(NetherChunkGenerator.class)
public class NetherChunkGeneratorMixin implements ChunkGeneratorInterface {
	@Shadow
	@Final
	private FortressStructure fortress;

	@Unique
	@Override
	public NbtList getBoundingBoxes(Entity entity) {
		NbtList nbttaglist = new NbtList();
		nbttaglist.addElement(((StructureFeatureInterface) this.fortress).getBoundingBoxes(entity, CarpetClientMarkers.FORTRESS));
		return nbttaglist;
	}
}
