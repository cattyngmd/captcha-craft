package dev.cattyn.captchacraft.config;

import com.google.common.collect.Lists;
import dev.cattyn.captchacraft.models.ThinkingLevel;
import eu.midnightdust.lib.config.MidnightConfig;

import java.util.List;

import static dev.cattyn.captchacraft.Constants.GOOGLE_MODEL;

public final class Config extends MidnightConfig {
    private static final String GENERAL = "general";
    private static final String PROXY = "proxy";
    private static final String SECRET = "secret";

    @Entry(category = GENERAL) public static boolean enabled = true;
    @Entry(category = GENERAL) public static String model = GOOGLE_MODEL;
    @Entry(category = GENERAL) public static ThinkingLevel thinking = ThinkingLevel.MINIMAL;
    @Entry(category = GENERAL, min = 0, max = 20) public static int delay = 1;
    @Entry(category = GENERAL) public static List<String> triggerList = Lists.newArrayList("Реши капчу", "Неверная капче");

    @Entry(category = PROXY) public static boolean proxyEnabled = false;
    @Entry(category = PROXY) public static String host = "127.0.0.1";
    @Entry(category = PROXY, min = 1, max = 65535) public static int port = 8080;
    @Entry(category = PROXY) public static String user = "";
    @Entry(category = PROXY) public static String password = "";

    @Entry(category = SECRET) public static String apiKey = "open router api key";
}
