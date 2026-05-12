package dev.cattyn.captchacraft.mixin;

import dev.cattyn.captchacraft.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener extends ClientCommonPacketListenerImpl {
    protected MixinClientPacketListener(Minecraft minecraft, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraft, connection, commonListenerCookie);
    }

    @Inject(method = "handleSystemChat", at = @At("TAIL"))
    private void handleMapItemDataHook(ClientboundSystemChatPacket packet, CallbackInfo ci) {
        if (Main.getChat().testMessage(packet.content())) {
            Main.getScheduler().schedule(Main.getSolver());
        }
    }
}
