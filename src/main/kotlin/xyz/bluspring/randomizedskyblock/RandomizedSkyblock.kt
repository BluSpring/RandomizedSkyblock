package xyz.bluspring.randomizedskyblock

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.block.*
import net.minecraft.entity.boss.BossBar
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.World
import net.minecraft.world.chunk.WorldChunk
import net.minecraft.world.gen.WorldPreset

private const val TICKS_30 = 600
private const val TICKS_60 = 1200

class RandomizedSkyblock : ModInitializer {
    private var ticks = 0
    private lateinit var randomItems: List<Item>
    private lateinit var bossBar: BossBar

    private lateinit var thread: Thread

    override fun onInitialize() {
        // Randomized item setup
        randomItems = Registry.ITEM.filter {
            val key = Registry.ITEM.getKey(it).get()

            isNotAny(
                key.value.path,
                "command_block", "repeating_command_block", "chain_command_block",
                "structure_block", "structure_void"
            )
        }

        ServerLifecycleEvents.SERVER_STARTED.register {
            bossBar = it.bossBarManager.add(bossBarId, formatText(60))
            bossBar.color = BossBar.Color.YELLOW
            bossBar.percent = 1F

            thread = Thread {
                ServerTickEvents.END_SERVER_TICK.register { _ ->
                    ticks++

                    bossBar.percent = ((TICKS_60 - ticks) / TICKS_60).toFloat()

                    if (ticks % 20 == 0) {
                        bossBar.name = formatText(60 - (ticks / 20))
                    }

                    if (ticks >= TICKS_60) {
                        it.playerManager.playerList.forEach { player ->
                            val random = randomItems.random()

                            player.giveItemStack(ItemStack(random.asItem(), 1))
                        }

                        ticks = 0
                    }
                }
            }

            thread.name = "RandomizedSkyblock Ticking Thread"
            thread.start()

            ServerChunkEvents.CHUNK_LOAD.register(ServerChunkEvents.Load { world, chunk ->
                val blockPosList = blocksToPlaceUponLoading[world] ?: return@Load

                val blockPosInThisChunk = blockPosList.filter { blockPos ->
                    val chunkPos = ChunkPos(blockPos)
                    chunkPos.x == chunk.pos.x && chunkPos.z == chunk.pos.z
                }

                blockPosInThisChunk.forEach { blockPos ->
                    world.setBlockState(blockPos, Blocks.STONE.defaultState)

                    blockPosList.remove(blockPos)
                }

                if (blockPosList.isEmpty()) {
                    blocksToPlaceUponLoading.remove(world)
                }
            })
        }

        ServerLifecycleEvents.SERVER_STOPPING.register {
            thread.interrupt()

            blocksToPlaceUponLoading.clear()
        }
    }

    private fun formatText(time: Int): Text {
        return Text.of("Giving random blocks in ")
            .copy()
            .formatted(Formatting.GREEN)
            .append(
                Text.of("$time seconds")
                    .copy()
                    .formatted(Formatting.YELLOW)
            ).append(
                Text.of("!")
                    .copy()
                    .formatted(Formatting.GREEN)
            )
    }

    private fun isNotAny(string: String, vararg excludes: String): Boolean {
        return !excludes.contains(string)
    }

    companion object {
        val bossBarId = Identifier("randomizedskyblock", "timer")
        val randomizedSkyblockWorld: RegistryKey<WorldPreset> = RegistryKey.of(Registry.WORLD_PRESET_KEY, Identifier("randomized_skyblock_world"))

        val blocksToPlaceUponLoading = mutableMapOf<World, MutableList<BlockPos>>()
    }
}