package xyz.bluspring.randomizedskyblock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.noise.NoiseConfig;
import xyz.bluspring.randomizedskyblock.generator.VoidGenerator;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class StupidWorkaround extends ChunkGenerator {
    public StupidWorkaround(Registry<StructureSet> structureSetRegistry, Optional<RegistryEntryList<StructureSet>> structureOverrides, BiomeSource biomeSource) {
        super(structureSetRegistry, structureOverrides, biomeSource);
    }

    public static Codec<VoidGenerator> getVoidGeneratorCodec() {
        return RecordCodecBuilder.create(
                (instance) ->
                        createStructureSetRegistryGetter(instance)
                                .apply(instance, instance.stable(VoidGenerator::new))
        );
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return null;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess world, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver carverStep) {

    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {

    }

    @Override
    public void populateEntities(ChunkRegion region) {

    }

    @Override
    public int getWorldHeight() {
        return 0;
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        return null;
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinimumY() {
        return 0;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        return 0;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        return null;
    }

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {

    }
}
