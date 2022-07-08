package xyz.bluspring.randomizedskyblock.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.bluspring.randomizedskyblock.RandomizedSkyblock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Shadow @Final private MinecraftServer server;

    private HashMap<ServerPlayerEntity, NbtCompound> compoundMap = new HashMap<>();

    @Inject(at = @At("TAIL"), method = "onPlayerConnect")
    public void addToBossbar(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        var bossBar = this.server.getBossBarManager().get(RandomizedSkyblock.Companion.getBossBarId());

        if (bossBar == null)
            return;

        bossBar.addPlayer(player);
    }
}
