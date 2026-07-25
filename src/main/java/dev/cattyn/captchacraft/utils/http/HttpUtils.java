package dev.cattyn.captchacraft.utils.http;

import com.google.gson.Gson;
import dev.cattyn.captchacraft.models.OpenRouterRequest;
import dev.cattyn.captchacraft.models.OpenRouterResponse;
import dev.cattyn.captchacraft.models.ProxyInfo;
import dev.cattyn.captchacraft.utils.exceptions.HttpResponseException;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class HttpUtils {
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    private static final Duration HTTP_TIMEOUT = Duration.ofSeconds(20);
    private static final Gson GSON = new Gson();
    private static final LazyHttpClient LAZY_CLIENT = new LazyHttpClient();

    private HttpUtils() {
    }

    public static CompletableFuture<OpenRouterResponse> request(ProxyInfo proxy, CaptchaRequest captcha) {
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

        HttpClient client = LAZY_CLIENT.acquire(proxy, HttpUtils::createClient);

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpUtils::getOut)
                .thenApply(json -> GSON.fromJson(json, OpenRouterResponse.class));
    }

    private static <T> T getOut(HttpResponse<T> response) {
        int code = response.statusCode();
        if (code == 407) {
            throw new HttpResponseException(code, "invalid proxy auth credentials");
        }
        if (code == 401) {
            throw new HttpResponseException(code, "invalid API credentials");
        }
        return response.body();
    }

    private static HttpClient createClient(ProxyInfo info) {
        HttpClient.Builder builder = HttpClient.newBuilder();
        builder.connectTimeout(HTTP_TIMEOUT);
        if (info != null) {
            builder.proxy(ProxySelector.of(info.address()));

            if (info.user() != null) {
                builder.authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(info.user(), info.password().toCharArray());
                    }
                });
            }
        }

        return builder.build();
    }
}