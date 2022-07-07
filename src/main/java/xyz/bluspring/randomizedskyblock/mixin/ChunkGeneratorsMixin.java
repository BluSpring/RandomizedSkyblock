package xyz.bluspring.randomizedskyblock.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGenerators;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.bluspring.randomizedskyblock.generator.VoidGenerator;

@Mixin(ChunkGenerators.class)
public class ChunkGeneratorsMixin {
    @Inject(at = @At("TAIL"), method = "registerAndGetDefault")
    private static void registerVoidGeneratorCodec(Registry<Codec<? extends ChunkGenerator>> registry, CallbackInfoReturnable<Codec<? extends ChunkGenerator>> cir) {
        Registry.register(registry, "randomized_skyblock", VoidGenerator.Companion.getCODEC());
    }
}
