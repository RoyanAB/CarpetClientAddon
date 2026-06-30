package cn.royan.carpetclientaddon.marks.mixins.village;

import cn.royan.carpetclientaddon.marks.CarpetClientMarkers;
import cn.royan.carpetclientaddon.marks.fakes.SavedVillageDataInterface;
import net.minecraft.world.World;
import net.minecraft.world.village.SavedVillageData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SavedVillageData.class)
public class SavedVillageDataMixin implements SavedVillageDataInterface {
	@Shadow
	private World world;

	@Unique
	private static boolean updateMarkers;

	@Unique
	@Override
	public void setUpdateMarkers(boolean value) {
		updateMarkers = value;
	}

	@Inject(method = "tick", at = @At(value = "HEAD"))
	private void onTick(CallbackInfo ci) {
		updateMarkers = false;
	}

	@Inject(method = "removeEmptyVillages", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/village/SavedVillageData;markDirty()V", shift = At.Shift.AFTER))
	private void onRemoveEmptyVillages(CallbackInfo ci) {
		updateMarkers = true;
	}

	@Inject(method = "addPendingDoors", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/village/Village;addDoor(Lnet/minecraft/world/village/VillageDoor;)V", shift = At.Shift.AFTER))
	private void onAddPendingDoors(CallbackInfo ci) {
		updateMarkers = true;
	}

	@Inject(method = "readNbt", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER))
	private void onReadNbt(CallbackInfo ci) {
		updateMarkers = true;
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/village/SavedVillageData;addPendingDoors()V", shift = At.Shift.AFTER))
	private void afterAddPendingDoors(CallbackInfo ci) {
		if (updateMarkers) CarpetClientMarkers.updateClientVillageMarkers(this.world);
	}
}
