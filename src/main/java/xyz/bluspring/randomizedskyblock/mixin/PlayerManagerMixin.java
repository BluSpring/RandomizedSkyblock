package xyz.bluspring.randomizedskyblock.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.bluspring.randomizedskyblock.RandomizedSkyblock;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Shadow @Final private MinecraftServer server;

    @Inject(at = @At("TAIL"), method = "onPlayerConnect")
    public void addToBossbar(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        var bossBar = this.server.getBossBarManager().get(RandomizedSkyblock.Companion.getBossBarId());

        if (bossBar == null)
            return;

        bossBar.addPlayer(player);
    }
}
