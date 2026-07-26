package dev.cattyn.captchacraft.models;

import java.net.InetSocketAddress;

public record ProxyInfo(InetSocketAddress address, String user, String password) {
    public static ProxyInfo create(String url, int port, String user, String password) {
        return new ProxyInfo(new InetSocketAddress(url, port), user, password);
    }

    public static ProxyInfo create(String url, int port) {
        return new ProxyInfo(new InetSocketAddress(url, port), null, null);
    }
}