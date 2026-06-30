package cn.royan.carpetclientaddon.mixins;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.SettingsManager;
import net.minecraft.server.command.source.CommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SettingsManager.class)
public interface SettingsManagerInvoker {
	@Invoker("setDefault")
	int invokeSetDefault(CommandSource source, CarpetRule<?> rule, String newValu);
}
