package cn.royan.carpetclientaddon.chunkdebugtool.mixins.block;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Block.class)
public class BlockMixin {
	@Inject(method = "randomTick", at = @At("HEAD"))
	public void onReasonLoggingStart(World world, BlockPos pos, BlockState state, Random random, CallbackInfo ci) {
		if (CarpetClientChunkLogger.logger.enabled)
			CarpetClientChunkLogger.setReason("randomTick");
	}

	@Inject(method = "randomTick", at = @At("TAIL"))
	public void onReasonLoggingEnd(World world, BlockPos pos, BlockState state, Random random, CallbackInfo ci) {
		CarpetClientChunkLogger.resetReason();
	}
}
