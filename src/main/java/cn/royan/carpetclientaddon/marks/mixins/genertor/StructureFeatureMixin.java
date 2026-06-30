package cn.royan.carpetclientaddon.marks.mixins.genertor;

import cn.royan.carpetclientaddon.marks.CarpetClientMarkers;
import cn.royan.carpetclientaddon.marks.fakes.StructureFeatureInterface;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.structure.StructureFeature;
import net.minecraft.world.gen.structure.StructurePiece;
import net.minecraft.world.gen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Iterator;

@Mixin(StructureFeature.class)
public class StructureFeatureMixin implements StructureFeatureInterface {
	@Shadow
	protected Long2ObjectMap<StructureStart> structures;

	@Unique
	@Override
	public NbtList getBoundingBoxes(Entity entity, int type) {
		NbtList nbttaglist = new NbtList();

		for (StructureStart structurestart : this.structures.values()) {
			if (MathHelper.sqrt(new ChunkPos(structurestart.getChunkX(), structurestart.getChunkZ()).squaredDistanceTo(entity)) > 700) {
				continue;
			}
			NbtCompound tagCompound = new NbtCompound();
			Iterator<StructurePiece> iterator = structurestart.getPieces().iterator();
			tagCompound.putInt("type", CarpetClientMarkers.OUTER_BOUNDING_BOX);
			tagCompound.put("bb", structurestart.getBounds().toNbt());
			nbttaglist.addElement(tagCompound);
			while (iterator.hasNext()) {
				NbtCompound compound = new NbtCompound();
				StructurePiece structurecomponent = iterator.next();
				compound.putInt("type", type);
				compound.put("bb", structurecomponent.getBounds().toNbt());
				nbttaglist.addElement(compound);
			}
		}
		return nbttaglist;
	}
}
