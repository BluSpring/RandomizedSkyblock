package xyz.bluspring.randomizedskyblock.mixin;

import net.minecraft.structure.StructureSet;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.WorldPreset;
import net.minecraft.world.gen.WorldPresets;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.bluspring.randomizedskyblock.RandomizedSkyblock;
import xyz.bluspring.randomizedskyblock.generator.VoidGenerator;

@Mixin(WorldPresets.Registrar.class)
public abstract class WorldPresetsRegistrarMixin {
    @Shadow protected abstract RegistryEntry<WorldPreset> register(RegistryKey<WorldPreset> key, DimensionOptions dimensionOptions);

    @Shadow protected abstract DimensionOptions createOverworldOptions(ChunkGenerator chunkGenerator);

    @Shadow @Final private Registry<StructureSet> structureSetRegistry;

    @Inject(at = @At("TAIL"), method = "initAndGetDefault")
    public void registerVoid(CallbackInfoReturnable<RegistryEntry<WorldPreset>> cir) {
        this.register(RandomizedSkyblock.Companion.getRandomizedSkyblockWorld(), this.createOverworldOptions(new VoidGenerator(this.structureSetRegistry)));
    }
}
