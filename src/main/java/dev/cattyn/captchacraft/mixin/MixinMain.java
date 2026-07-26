package dev.cattyn.captchacraft.mixin;

import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MixinMain {
    // the only way to handle it is to set those props
    // or use external http client that i don't want to do
    // https://www.oracle.com/java/technologies/javase/8all-relnotes.html#:~:text=Disable%20Basic%20authentication%20for%20HTTPS%20tunneling
    @Inject(method = "main", at = @At("HEAD"))
    private static void mainHook(String[] strings, CallbackInfo ci) {
        System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
        System.setProperty("jdk.http.auth.proxying.disabledSchemes", "");
    }
}
