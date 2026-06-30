package cn.royan.carpetclientaddon.chunkdebugtool.mixins.entity;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.village.Village;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Village.class)
public class VillageMixin {
	@Redirect(method = "isWoodenDoor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/BlockState;"))
	private BlockState redirectGetBlockState(World instance, BlockPos pos) {
		String prevReason = CarpetClientChunkLogger.reason.get();
		CarpetClientChunkLogger.reason.set("Village loading");
		BlockState prev = instance.getBlockState(pos);
		CarpetClientChunkLogger.reason.set(prevReason);
		return prev;
	}
}
