package cn.royan.carpetclientaddon.mixins;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.SettingsManager;
import cn.royan.carpetclientaddon.rulesender.CarpetClientRuleChanger;
import net.minecraft.server.command.source.CommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SettingsManager.class)
public class SettingsManagerMixin {
	@Inject(method = "setRule", at = @At("RETURN"), remap = false)
	private void onSetRule(CommandSource source, CarpetRule<?> rule, String newValue, CallbackInfoReturnable<Integer> cir) {
		CarpetClientRuleChanger.updateCarpetClientsRule(rule.name(), newValue);
	}

	@Inject(method = "setDefault", at = @At("RETURN"), remap = false)
	private void onSetDefault(CommandSource source, CarpetRule<?> rule, String newValue, CallbackInfoReturnable<Integer> cir) {
		CarpetClientRuleChanger.updateCarpetClientsRule(rule.name(), newValue);
	}
}
