package dev.cattyn.captchacraft.handlers;

import dev.cattyn.captchacraft.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ChatHandler {
    public boolean testMessage(Component component) {
        String message = ChatFormatting.stripFormatting(component.getString());
        for (String s : Config.triggerList) {
            if (message.startsWith(s)) return true;
        }
        return false;
    }
}
