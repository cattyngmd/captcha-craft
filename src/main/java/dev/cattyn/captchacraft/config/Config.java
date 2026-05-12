package dev.cattyn.captchacraft.config;

import com.google.common.collect.Lists;
import dev.cattyn.captchacraft.models.ThinkingLevel;
import eu.midnightdust.lib.config.MidnightConfig;

import java.util.List;

import static dev.cattyn.captchacraft.Constants.GOOGLE_MODEL;

public final class Config extends MidnightConfig {
    private static final String GENERAL = "general";
    private static final String SECRET = "secret";

    @Entry(category = GENERAL) public static boolean enabled = true;
    @Entry(category = GENERAL) public static String model = GOOGLE_MODEL;
    @Entry(category = GENERAL) public static ThinkingLevel thinking = ThinkingLevel.MINIMAL;
    @Entry(category = GENERAL, min = 0, max = 20) public static int delay = 1;
    @Entry(category = GENERAL) public static List<String> triggerList = Lists.newArrayList("Реши капчу", "Неверная капча");

    @Entry(category = SECRET) public static String apiKey = "open router api key";
}
