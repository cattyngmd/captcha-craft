package dev.cattyn.captchacraft.utils.http;

import dev.cattyn.captchacraft.models.ThinkingLevel;

import static dev.cattyn.captchacraft.Constants.GOOGLE_MODEL;

public record CaptchaRequest(String secret, byte[] imageData, String model, ThinkingLevel thinking) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String secret;
        private byte[] imageData;
        private String model = GOOGLE_MODEL;
        private ThinkingLevel thinking = ThinkingLevel.MINIMAL;

        public Builder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public Builder imageData(byte[] imageData) {
            this.imageData = imageData;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder thinking(ThinkingLevel thinking) {
            this.thinking = thinking;
            return this;
        }

        public CaptchaRequest build() {
            if (secret == null || imageData == null) {
                throw new IllegalStateException("Secret and imageData are required");
            }
            return new CaptchaRequest(secret, imageData, model, thinking);
        }
    }

}
