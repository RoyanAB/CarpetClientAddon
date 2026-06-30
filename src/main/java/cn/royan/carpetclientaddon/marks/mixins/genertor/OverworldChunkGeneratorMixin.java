package cn.royan.carpetclientaddon.marks.mixins.genertor;

import cn.royan.carpetclientaddon.marks.CarpetClientMarkers;
import cn.royan.carpetclientaddon.marks.fakes.ChunkGeneratorInterface;
import cn.royan.carpetclientaddon.marks.fakes.StructureFeatureInterface;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.structure.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin implements ChunkGeneratorInterface {
	@Shadow
	@Final
	private VillageStructure village;

	@Shadow
	@Final
	private StrongholdStructure stronghold;

	@Shadow
	@Final
	private MineshaftStructure mineshaft;

	@Shadow
	@Final
	private OceanMonumentStructure oceanMonument;

	@Shadow
	@Final
	private MansionStructure mansion;

	@Shadow
	@Final
	private TempleStructure witchHut;

	@Unique
	@Override
	public NbtList getBoundingBoxes(Entity entity) {
		NbtList nbttaglist = new NbtList();
		nbttaglist.addElement(((StructureFeatureInterface) this.witchHut).getBoundingBoxes(entity, CarpetClientMarkers.TEMPLE));
		nbttaglist.addElement(((StructureFeatureInterface) this.village).getBoundingBoxes(entity, CarpetClientMarkers.VILLAGE));
		nbttaglist.addElement(((StructureFeatureInterface) this.stronghold).getBoundingBoxes(entity, CarpetClientMarkers.STRONGHOLD));
		nbttaglist.addElement(((StructureFeatureInterface) this.mineshaft).getBoundingBoxes(entity, CarpetClientMarkers.MINESHAFT));
		nbttaglist.addElement(((StructureFeatureInterface) this.oceanMonument).getBoundingBoxes(entity, CarpetClientMarkers.MONUMENT));
		nbttaglist.addElement(((StructureFeatureInterface) this.mansion).getBoundingBoxes(entity, CarpetClientMarkers.MANSION));
		return nbttaglist;
	}
}
