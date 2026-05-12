package dev.cattyn.captchacraft.handlers;

import dev.cattyn.captchacraft.config.Config;
import dev.cattyn.captchacraft.models.OpenRouterResponse;
import dev.cattyn.captchacraft.utils.http.CaptchaRequest;
import dev.cattyn.captchacraft.utils.http.HttpUtils;
import dev.cattyn.captchacraft.utils.MapUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static dev.cattyn.captchacraft.utils.ChatUtils.print;
import static dev.cattyn.captchacraft.utils.ChatUtils.send;

public class SolverHandler {
    private final AtomicBoolean solving = new AtomicBoolean(false);

    public void solve(ItemStack stack, Level level) {
        if (isInvalidState()) return;

        MapId id = stack.get(DataComponents.MAP_ID);
        if (id == null) return;

        MapItemSavedData mapData = level.getMapData(id);
        if (mapData == null) return;

        processCaptcha(mapData);
    }

    private void processCaptcha(MapItemSavedData mapData) {
        if (!solving.compareAndSet(false, true)) return;

        try {
            byte[] data = MapUtils.toPng(mapData);
            CaptchaRequest request = buildRequest(data);
            print("{gray}Captcha solver started");
            executeRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
            solving.set(false);
        }
    }

    private void executeRequest(CaptchaRequest captcha) {
        long startTime = System.currentTimeMillis();

        HttpUtils.request(captcha)
                .thenAccept(response -> handleResponse(response, startTime))
                .exceptionally(ex -> {
                    handleError(ex);
                    return null;
                })
                .whenComplete((v, t) -> solving.set(false));
    }

    private void handleResponse(OpenRouterResponse response, long startTime) {
        if (response.choices().isEmpty()) {
            handleError(new RuntimeException("API returned empty choices"));
            return;
        }

        String result = response.choices().getFirst().message().content().trim();
        long duration = System.currentTimeMillis() - startTime;

        sync(() -> {
            print("{green}Captcha solved: {white}" + result + "{gray} in " + duration + "ms");
            send(result);
        });
    }

    private void handleError(Throwable ex) {
        ex.printStackTrace();
        sync(() -> print("{red}Captcha solver failed: " + ex.getLocalizedMessage()));
    }

    private CaptchaRequest buildRequest(byte[] data) {
        return CaptchaRequest.builder()
                .secret(Config.apiKey)
                .model(Config.model)
                .thinking(Config.thinking)
                .imageData(data)
                .build();
    }

    private void sync(Runnable runnable) {
        Minecraft.getInstance().execute(runnable);
    }

    private boolean isInvalidState() {
        return Minecraft.getInstance().player == null || Minecraft.getInstance().level == null || !Config.enabled;
    }

    public boolean isSolving() {
        return solving.get();
    }
}