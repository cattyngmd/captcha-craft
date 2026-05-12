package dev.cattyn.captchacraft.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record OpenRouterResponse(@SerializedName("choices") List<Choice> choices,
                                 @SerializedName("usage") Usage usage) {
    public record Choice(@SerializedName("message") MessageResponse message) {
    }

    public record MessageResponse(@SerializedName("content") String content) {
    }

    public record Usage(@SerializedName("prompt_tokens") int promptTokens,
                        @SerializedName("completion_tokens") int completionTokens) {
    }
}