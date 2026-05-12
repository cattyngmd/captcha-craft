package dev.cattyn.captchacraft.mixin;

import com.mojang.authlib.GameProfile;
import dev.cattyn.captchacraft.Main;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends AbstractClientPlayer {
    public MixinLocalPlayer(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickHook(CallbackInfo ci) {
        if (Main.getScheduler().schedulePassed()) {
            Main.getSolver().solve(getMainHandItem(), level());
        }
    }
}
