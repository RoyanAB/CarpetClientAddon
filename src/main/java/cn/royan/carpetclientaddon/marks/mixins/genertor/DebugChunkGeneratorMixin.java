package cn.royan.carpetclientaddon.marks.mixins.genertor;

import cn.royan.carpetclientaddon.marks.fakes.ChunkGeneratorInterface;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.gen.chunk.DebugChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DebugChunkGenerator.class)
public class DebugChunkGeneratorMixin implements ChunkGeneratorInterface {
	@Unique
	@Override
	public NbtList getBoundingBoxes(Entity entity) {
		return new NbtList();
	}
}
