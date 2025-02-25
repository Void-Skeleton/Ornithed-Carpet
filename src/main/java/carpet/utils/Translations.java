package carpet.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import carpet.server.CarpetExtension;
import carpet.server.CarpetServer;
import carpet.settings.CarpetSettings;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Translations {
    private static Map<String, String> translationMap = Collections.emptyMap();

    public static String tr(String key) {
        return translationMap.getOrDefault(key, key);
    }

    public static String trOrNull(String key) {
        return translationMap.get(key);
    }

    public static String tr(String key, String str) {
        return translationMap.getOrDefault(key, str);
    }

    public static boolean hasTranslations() {
        return !translationMap.isEmpty();
    }

    public static boolean hasTranslation(String key) {
        return translationMap.containsKey(key);
    }

    public static Map<String, String> getTranslationFromResourcePath(String path) {
        InputStream langFile = Translations.class.getClassLoader().getResourceAsStream(path);
        if (langFile == null) {
            // we don't have that language
            return Collections.emptyMap();
        }
        Gson gson = new GsonBuilder().create();
		return gson.fromJson(new InputStreamReader(langFile, StandardCharsets.UTF_8), new TypeToken<Map<String, String>>(){}.getType());
    }

    public static void updateLanguage() {
        Map<String, String> translations = new HashMap<>();
        translations.putAll(getTranslationFromResourcePath(String.format("assets/ornithe_carpet/lang/%s.json", CarpetSettings.language)));

        for (CarpetExtension ext : CarpetServer.extensions) {
            Map<String, String> extMappings = ext.canHasTranslations(CarpetSettings.language);
            if (extMappings == null) continue; // would be nice to get rid of this, but too many extensions return null where they don't know they do
            boolean warned = false;
            for (Map.Entry<String, String> entry : extMappings.entrySet()) {
                String key = entry.getKey();
                // Migrate the old format
                if (!key.startsWith("carpet.")) {
                    if (key.startsWith("rule.")) {
                        key = String.format(TranslationKeys.BASE_RULE_NAMESPACE, "carpet") + key.substring(5);
                    } else if (key.startsWith("category.")) {
                        key = String.format(TranslationKeys.CATEGORY_PATTERN, "carpet", key.substring(9));
                    }
                    if (!warned && !key.equals(entry.getKey())) {
                        CarpetSettings.LOG.warn(String.format("Found outdated translation keys in extension '%s'!\n" +
								"These won't be supported in a later Carpet version!\n" +
								"Carpet will now try to map them to the correct keys in a best-effort basis", ext.getClass().getName()));
                        warned = true;
                    }
                }
                translations.putIfAbsent(key, entry.getValue());
            }
        }
        translations.keySet().removeIf(e -> {
            if (e.startsWith("//")) {
                CarpetSettings.LOG.warn(String.format("Found translation key starting with // while preparing translations!\n" +
						"Doing this is deprecated and may cause issues in later versions! Consider settings GSON to 'lenient' mode and\n" +
						"using regular comments instead!\n" +
						"Translation key is '%s'", e));
                return true;
            } else
                return false;
        });
        translationMap = translations;
    }
}
