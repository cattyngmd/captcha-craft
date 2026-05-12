package dev.cattyn.captchacraft.utils;

import dev.cattyn.catformat.CatFormat;
import dev.cattyn.catformat.CatFormatImpl;
import dev.cattyn.catformat.text.Modifier;
import dev.cattyn.catformat.text.TextWrapper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.awt.*;

public final class ChatUtils {
    private static final Component PREFIX = Component.literal("<CaptchaCraft>").withStyle(ChatFormatting.GREEN);
    private static final CatFormat<MutableComponent> FORMAT;

    private ChatUtils() {
    }

    public static void send(String message) {
        if (Minecraft.getInstance().getConnection() == null) return;
        Minecraft.getInstance().getConnection().sendChat(message);
    }

    public static void print(String text) {
        print(FORMAT.format(text));
    }

    public static void print(String text, Object... obj) {
        print(FORMAT.format(text, obj));
    }

    public static void print(Component component) {
        Minecraft.getInstance().gui.getChat().addMessage(Component.empty().append(PREFIX).append(" ").append(component));
    }

    static {
        FORMAT = new CatFormatImpl<>(new MinecraftWrapper());
        for (var value : ChatFormatting.values()) {
            if (!value.isColor() || value.getColor() == null) {
                continue;
            }
            FORMAT.add(value.getName(), value.getColor());
        }
    }

    private static class MinecraftWrapper implements TextWrapper<MutableComponent> {
        @Override
        public MutableComponent colored(MutableComponent component, int i) {
            return component.withColor(i);
        }

        @Override
        public MutableComponent concat(MutableComponent component, MutableComponent component2) {
            return component.append(component2);
        }

        @Override
        public MutableComponent modify(MutableComponent component, int modifiers) {
            if (Modifier.BOLD.isIn(modifiers)) component.withStyle(ChatFormatting.BOLD);
            if (Modifier.ITALIC.isIn(modifiers)) component.withStyle(ChatFormatting.ITALIC);
            if (Modifier.UNDERLINE.isIn(modifiers)) component.withStyle(ChatFormatting.UNDERLINE);
            if (Modifier.STRIKETHROUGH.isIn(modifiers)) component.withStyle(ChatFormatting.STRIKETHROUGH);
            if (Modifier.OBFUSCATED.isIn(modifiers)) component.withStyle(ChatFormatting.OBFUSCATED);
            return component;
        }

        @Override
        public MutableComponent newText(String s) {
            return Component.literal(s);
        }
    }
}
