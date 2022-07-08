package xyz.bluspring.randomizedskyblock.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.bluspring.randomizedskyblock.RandomizedSkyblock;

import java.util.HashMap;

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

    @Inject(at = @At("TAIL"), method = "onPlayerConnect")
    public void addBlockBelowPlayer(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        var nbtCompound = compoundMap.get(player);

        if (nbtCompound == null) {
            // Set bedrock below player.
            player.getWorld().setBlockState(player.getBlockPos().add(0, -1, 0), Blocks.BEDROCK.getDefaultState());
        }

        compoundMap.remove(player);
    }

    // Y'know, people would not make this incredibly stupid workaround normally,
    // but because local capture wasn't working, I had no other choice.
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;loadPlayerData(Lnet/minecraft/server/network/ServerPlayerEntity;)Lnet/minecraft/nbt/NbtCompound;"), method = "onPlayerConnect")
    public NbtCompound incrediblyStupidWorkaroundToGetAroundBrokenLocalCapture(PlayerManager instance, ServerPlayerEntity player) {
        var nbtCompound = instance.loadPlayerData(player);

        compoundMap.put(player, nbtCompound);

        return nbtCompound;
    }
}
