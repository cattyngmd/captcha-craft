package dev.cattyn.captchacraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import dev.cattyn.captchacraft.Constants;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandBuildContext;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public final class MainCommand implements ClientCommandRegistrationCallback {
    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext access) {
        dispatcher.register(
                literal(Constants.MOD_ID).executes(ctx -> {
                    Minecraft mc = ctx.getSource().getClient();
                    mc.execute(() -> mc.setScreen(MidnightConfig.getScreen(mc.screen, Constants.MOD_ID)));
                    return 1;
                })
        );
    }
}
