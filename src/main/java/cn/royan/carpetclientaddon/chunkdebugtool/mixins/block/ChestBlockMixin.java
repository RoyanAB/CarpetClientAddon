package cn.royan.carpetclientaddon.chunkdebugtool.mixins.block;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChestBlock.class)
public class ChestBlockMixin {
	@Redirect(method = "getShape", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldView;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/BlockState;"))
	private BlockState redirectGetBlockState0(WorldView instance, BlockPos pos) {
		String prevReason = CarpetClientChunkLogger.reason.get();
		CarpetClientChunkLogger.reason.set("Chest loading");
		BlockState prev = instance.getBlockState(pos);
		CarpetClientChunkLogger.reason.set(prevReason);
		return prev;
	}

	@Redirect(method = "onAdded", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/BlockState;"))
	private BlockState redirectGetBlockState1(World instance, BlockPos pos) {
		String prevReason = CarpetClientChunkLogger.reason.get();
		CarpetClientChunkLogger.reason.set("Chest loading");
		BlockState prev = instance.getBlockState(pos);
		CarpetClientChunkLogger.reason.set(prevReason);
		return prev;
	}

	@Redirect(method = "updateState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/BlockState;"))
	private BlockState redirectGetBlockState2(World instance, BlockPos pos) {
		String prevReason = CarpetClientChunkLogger.reason.get();
		CarpetClientChunkLogger.reason.set("Chest loading");
		BlockState prev = instance.getBlockState(pos);
		CarpetClientChunkLogger.reason.set(prevReason);
		return prev;
	}

	@Redirect(method = "isDoubleChest", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/BlockState;", ordinal = 1))
	private BlockState redirectGetBlockState3(World instance, BlockPos pos) {
		String prevReason = CarpetClientChunkLogger.reason.get();
		CarpetClientChunkLogger.reason.set("Chest loading");
		BlockState prev = instance.getBlockState(pos);
		CarpetClientChunkLogger.reason.set(prevReason);
		return prev;
	}

	@Redirect(method = "getCombinedMenuProvider", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/BlockState;"))
	private BlockState redirectGetBlockState4(World instance, BlockPos pos) {
		String prevReason = CarpetClientChunkLogger.reason.get();
		CarpetClientChunkLogger.reason.set("Chest loading");
		BlockState prev = instance.getBlockState(pos);
		CarpetClientChunkLogger.reason.set(prevReason);
		return prev;
	}
}
