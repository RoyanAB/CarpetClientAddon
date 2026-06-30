package cn.royan.carpetclientaddon.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class Translations {
	private static Map<String, String> translationMap;

	public static Map<String, String> getTranslationFromResourcePath(String lang) {
		InputStream langFile = Translations.class.getClassLoader().getResourceAsStream(String.format("assets/subtick/lang/%s.json", lang));
		if (langFile == null) {
			if (lang.equals("en_us"))
				return Collections.emptyMap();
			else
				getTranslationFromResourcePath("en_us");
		}
		String jsonData;
		try {
			jsonData = IOUtils.toString(langFile, StandardCharsets.UTF_8);
		} catch (IOException e) {
			return Collections.emptyMap();
		}
		Gson gson = new GsonBuilder().setLenient().create(); // lenient allows for comments
		translationMap = gson.fromJson(jsonData, new TypeToken<Map<String, String>>() {
		}.getType());
		return translationMap;
	}
}
