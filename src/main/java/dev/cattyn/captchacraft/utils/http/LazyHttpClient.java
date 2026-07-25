package dev.cattyn.captchacraft.utils.http;

import dev.cattyn.captchacraft.models.ProxyInfo;

import java.net.http.HttpClient;
import java.util.Objects;
import java.util.function.Function;

public final class LazyHttpClient {
    private ProxyInfo currentInfo;
    private HttpClient currentClient;

    public synchronized HttpClient acquire(ProxyInfo info, Function<ProxyInfo, HttpClient> factory) {
        if (currentClient == null || !Objects.equals(info, currentInfo)) {
            if (currentClient != null) {
                currentClient.shutdownNow();
            }
            currentClient = factory.apply(info);
            currentInfo = info;
        }
        return currentClient;
    }
}
