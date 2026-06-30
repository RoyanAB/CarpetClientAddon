package cn.royan.carpetclientaddon.chunkdebugtool.mixins.blockentity;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin {
	@Redirect(method = "isChestOfSameType", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/BlockState;"))
	private BlockState redirectGetBlockState0(World instance, BlockPos pos) {
		String prevReason = CarpetClientChunkLogger.reason.get();
		CarpetClientChunkLogger.reason.set("Chest loading");
		BlockState prev = instance.getBlockState(pos);
		CarpetClientChunkLogger.reason.set(prevReason);
		return prev;
	}
}
