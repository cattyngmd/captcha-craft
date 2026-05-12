package dev.cattyn.captchacraft.models;

import com.google.gson.annotations.SerializedName;

public enum ThinkingLevel {
    @SerializedName("minimal") MINIMAL,
    @SerializedName("low") LOW,
    @SerializedName("medium") MEDIUM,
    @SerializedName("high") HIGH;

    public String getValue() {
        return this.name().toLowerCase();
    }
}