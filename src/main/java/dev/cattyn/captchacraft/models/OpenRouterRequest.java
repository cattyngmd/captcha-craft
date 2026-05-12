package dev.cattyn.captchacraft.models;

import com.google.gson.annotations.SerializedName;

import java.util.Base64;
import java.util.List;

public record OpenRouterRequest(@SerializedName("model") String model,
                                @SerializedName("messages") List<Message> messages,
                                @SerializedName("temperature") double temperature,
                                @SerializedName("thinking_config") Thinking thinkingConfig) {

    public record Thinking(
            @SerializedName("thinking_level") ThinkingLevel thinkingLevel,
            @SerializedName("include_thoughts") boolean includeThoughts
    ) {
    }

    public record Message(@SerializedName("role") String role, @SerializedName("content") List<Content> content) {
    }

    public record Content(@SerializedName("type") String type,
                          @SerializedName("text") String text,
                          @SerializedName("image_url") ImageUrl imageUrl) {
        public static Content text(String text) {
            return new Content("text", text, null);
        }

        public static Content image(String base64) {
            return new Content("image_url", null, new ImageUrl("data:image/png;base64," + base64));
        }

        public static Content image(byte[] bytes) {
            String base64 = Base64.getEncoder().encodeToString(bytes);
            return image(base64);
        }
    }

    public record ImageUrl(String url) {
    }
}