package net.kore.pronouns.neoforge.mixin;

import net.kore.pronouns.neoforge.PronounsNeoForge;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method= "spin(Ljava/util/function/Function;)Lnet/minecraft/server/MinecraftServer;", at=@At("RETURN"))
    private static <S extends MinecraftServer> void injectSpin(Function<Thread, S> function, CallbackInfoReturnable<S> cir) {
        PronounsNeoForge.server = cir.getReturnValue();
    }
}
