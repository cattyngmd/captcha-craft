package dev.cattyn.captchacraft.utils.http;

import com.google.gson.Gson;
import dev.cattyn.captchacraft.models.OpenRouterRequest;
import dev.cattyn.captchacraft.models.OpenRouterResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class HttpUtils {
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    private static final Duration HTTP_TIMEOUT = Duration.ofSeconds(20);
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(HTTP_TIMEOUT)
            .build();
    private static final Gson GSON = new Gson();

    private HttpUtils() {
    }

    public static CompletableFuture<OpenRouterResponse> request(CaptchaRequest captcha) {
        var requestBody = new OpenRouterRequest(
                captcha.model(),
                List.of(new OpenRouterRequest.Message("user", List.of(
                        OpenRouterRequest.Content.text("Solve this captcha. Return only the text. Only possible symbols are A-Z 0-9"),
                        OpenRouterRequest.Content.image(captcha.imageData())
                ))),
                0.1,
                new OpenRouterRequest.Thinking(
                        captcha.thinking(),
                        false
                )
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .timeout(HTTP_TIMEOUT)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + captcha.secret())
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(requestBody)))
                .build();

        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(json -> GSON.fromJson(json, OpenRouterResponse.class));
    }
}