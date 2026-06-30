package cn.royan.carpetclientaddon.chunkdebugtool.mixins.blockentity;

import cn.royan.carpetclientaddon.chunkdebugtool.CarpetClientChunkLogger;
import net.minecraft.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
	@Unique
	private static String prevReason = "";

	@Inject(method = "pushItem()Z", at = @At("HEAD"))
	private void onReasonLogging1(CallbackInfoReturnable<Boolean> cir) {
		prevReason = CarpetClientChunkLogger.reason.get();
		CarpetClientChunkLogger.reason.set("Hopper loading");
	}

	@Inject(method = "pushItem()Z", at = @At(value = "INVOKE", target = "net/minecraft/block/entity/HopperBlockEntity.getTargetInventory ()Lnet/minecraft/inventory/Inventory;", shift = At.Shift.AFTER))
	private void onReasonLogging2(CallbackInfoReturnable<Boolean> cir) {
		CarpetClientChunkLogger.reason.set(prevReason);
	}

	@Inject(method = "pullItem(Lnet/minecraft/inventory/Hopper;)Z", at = @At("HEAD"))
	private static void onReasonLogging3(CallbackInfoReturnable<Boolean> cir) {
		prevReason = CarpetClientChunkLogger.reason.get();
		CarpetClientChunkLogger.reason.set("Hopper self-loading");

	}

	@Inject(method = "pullItem(Lnet/minecraft/inventory/Hopper;)Z", at = @At(value = "INVOKE", target = "net/minecraft/block/entity/HopperBlockEntity.getInventoryAbove (Lnet/minecraft/inventory/Hopper;)Lnet/minecraft/inventory/Inventory;", shift = At.Shift.AFTER))
	private static void onReasonLogging4(CallbackInfoReturnable<Boolean> cir) {
		CarpetClientChunkLogger.reason.set(prevReason);
	}
}
