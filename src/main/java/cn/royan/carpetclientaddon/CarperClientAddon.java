package cn.royan.carpetclientaddon;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import cn.royan.carpetclientaddon.chunkdebugtool.deobfuscator.StackTraceDeobfuscator;
import cn.royan.carpetclientaddon.network.CarpetClientServer;
import cn.royan.carpetclientaddon.network.util.PluginChannelManager;
import cn.royan.carpetclientaddon.util.Translations;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.handler.CommandRegistry;
import net.ornithemc.osl.entrypoints.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class CarperClientAddon implements ModInitializer, CarpetExtension {
	public static final Logger LOGGER = LogManager.getLogger("carpetclientaddon");

	public static final String MOD_ID = "carpetclientaddon";
	public static String MOD_VERSION = "unknown";
	public static String MOD_NAME = "unknown";

	public static PluginChannelManager pluginChannels;
	private static CarpetClientServer CCServer;

	public static final boolean hasSubtick = FabricLoader.getInstance().isModLoaded("subtick");

	@Override
	public void init() {
		ModMetadata metadata = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata();
		MOD_NAME = metadata.getName();
		MOD_VERSION = metadata.getVersion().getFriendlyString();

		StackTraceDeobfuscator.fetchMapping();
	}

	public static void onServerInit(MinecraftServer server) {
		pluginChannels = new PluginChannelManager(server);

		CCServer = new CarpetClientServer();
		pluginChannels.register(CCServer);
	}

	@Override
	public String version() {
		return MOD_ID;
	}

	public static void loadExtension() {
		CarpetServer.manageExtension(new CarperClientAddon());
	}

	@Override
	public void onGameStarted() {
		CarpetServer.settingsManager.parseSettingsClass(CarpetSettings.class);
	}

	@Override
	public void registerCommands(CommandRegistry registry) {

	}

	@Override
	public Map<String, String> canHasTranslations(String lang) {
		return Translations.getTranslationFromResourcePath(lang);
	}
}
