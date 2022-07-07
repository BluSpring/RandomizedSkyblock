package xyz.bluspring.randomizedskyblock.generator

import com.mojang.serialization.Codec
import net.minecraft.block.Blocks
import net.minecraft.structure.StructureSet
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.ChunkRegion
import net.minecraft.world.HeightLimitView
import net.minecraft.world.Heightmap
import net.minecraft.world.biome.BiomeKeys
import net.minecraft.world.biome.source.BiomeAccess
import net.minecraft.world.biome.source.FixedBiomeSource
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.chunk.Blender
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.chunk.VerticalBlockSample
import net.minecraft.world.gen.noise.NoiseConfig
import xyz.bluspring.randomizedskyblock.StupidWorkaround
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

class VoidGenerator(
    structureSetRegistry: Registry<StructureSet>
) : ChunkGenerator(structureSetRegistry, Optional.empty(), FixedBiomeSource(BuiltinRegistries.BIOME.getEntry(BiomeKeys.SNOWY_TAIGA).get())) {
    override fun getCodec(): Codec<VoidGenerator> {
        return CODEC
    }

    override fun carve(
        chunkRegion: ChunkRegion,
        seed: Long,
        noiseConfig: NoiseConfig,
        world: BiomeAccess,
        structureAccessor: StructureAccessor,
        chunk: Chunk,
        carverStep: GenerationStep.Carver
    ) {
    }

    override fun buildSurface(
        region: ChunkRegion,
        structures: StructureAccessor,
        noiseConfig: NoiseConfig,
        chunk: Chunk
    ) {
    }

    override fun populateEntities(region: ChunkRegion) {
    }

    override fun getWorldHeight(): Int {
        return 384
    }

    override fun populateNoise(
        executor: Executor,
        blender: Blender,
        noiseConfig: NoiseConfig,
        structureAccessor: StructureAccessor,
        chunk: Chunk
    ): CompletableFuture<Chunk> {
        return CompletableFuture.completedFuture(chunk)
    }

    override fun getSeaLevel(): Int {
        return -63
    }

    override fun getMinimumY(): Int {
        return 0
    }

    override fun getHeight(
        x: Int,
        z: Int,
        heightmap: Heightmap.Type,
        world: HeightLimitView,
        noiseConfig: NoiseConfig
    ): Int {
        return world.bottomY
    }

    override fun getColumnSample(
        x: Int,
        z: Int,
        world: HeightLimitView,
        noiseConfig: NoiseConfig

    ): VerticalBlockSample {
        return VerticalBlockSample(world.bottomY, arrayOf(Blocks.AIR.defaultState))
    }

    override fun getDebugHudText(text: MutableList<String>, noiseConfig: NoiseConfig, pos: BlockPos) {
    }

    companion object {
        val CODEC: Codec<VoidGenerator> = StupidWorkaround.getVoidGeneratorCodec()
    }
}