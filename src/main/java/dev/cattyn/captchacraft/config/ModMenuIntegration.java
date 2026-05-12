package dev.cattyn.captchacraft.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.cattyn.captchacraft.Constants;
import eu.midnightdust.lib.config.MidnightConfig;

public final class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, Constants.MOD_ID);
    }
}
