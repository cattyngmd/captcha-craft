package dev.cattyn.captchacraft.utils.exceptions;

public class HttpResponseException extends RuntimeException {
    public HttpResponseException(int code, String message) {
        super(code + " - " + message);
    }
}
