package cn.royan.carpetclientaddon.marks.mixins.village;

import cn.royan.carpetclientaddon.marks.fakes.SavedVillageDataInterface;
import net.minecraft.world.village.SavedVillageData;
import net.minecraft.world.village.Village;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Village.class)
public class VillageMixin {
	@Inject(method = "removeInvalidDoors", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;remove()V", shift = At.Shift.AFTER))
	private void onRemoveEmptyVillages(CallbackInfo ci) {
		SavedVillageData savedVillageData = new SavedVillageData((String) null);
		((SavedVillageDataInterface) savedVillageData).setUpdateMarkers(true);
	}
}
