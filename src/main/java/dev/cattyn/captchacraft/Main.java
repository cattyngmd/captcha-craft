package dev.cattyn.captchacraft;

import dev.cattyn.captchacraft.commands.MainCommand;
import dev.cattyn.captchacraft.config.Config;
import dev.cattyn.captchacraft.handlers.ChatHandler;
import dev.cattyn.captchacraft.handlers.ScheduleHandler;
import dev.cattyn.captchacraft.handlers.SolverHandler;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import static dev.cattyn.captchacraft.Constants.MOD_ID;

public class Main implements ModInitializer {
    private static SolverHandler solver;
    private static ScheduleHandler scheduler;
    private static ChatHandler chat;

    @Override
    public void onInitialize() {
        MidnightConfig.init(MOD_ID, Config.class);
        solver = new SolverHandler();
        chat = new ChatHandler();
        scheduler = new ScheduleHandler();

        ClientCommandRegistrationCallback.EVENT.register(new MainCommand());
    }

    public static SolverHandler getSolver() {
        return solver;
    }

    public static ScheduleHandler getScheduler() {
        return scheduler;
    }

    public static ChatHandler getChat() {
        return chat;
    }
}
