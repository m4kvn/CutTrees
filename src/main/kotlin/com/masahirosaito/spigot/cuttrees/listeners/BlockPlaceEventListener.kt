package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.database.BlockObject
import com.masahirosaito.spigot.cuttrees.database.ChunkObject
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.material.Tree
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class BlockPlaceEventListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBLockPlace(event: BlockPlaceEvent) {
        if (event.isCancelled) return
        if (event.block.state.data !is Tree) return

        val block = event.block
        val chunk = block.chunk
        val uuid = block.world.uid
        val chunkX = chunk.x
        val chunkZ = chunk.z

        transaction {
            val selectChunks = ChunkObject.select {
                ((ChunkObject.worldUid eq uuid)
                        and (ChunkObject.x eq chunkX)
                        and (ChunkObject.z eq chunkZ))
            }

            val cId = if (selectChunks.none()) {
                ChunkObject.insert {
                    it[worldUid] = uuid
                    it[x] = event.block.chunk.x
                    it[z] = event.block.chunk.z
                }[ChunkObject.id]
            } else selectChunks.first()[ChunkObject.id]

            val selectBlocks = BlockObject.select {
                ((BlockObject.chunkId eq cId)
                        and (BlockObject.x eq block.x)
                        and (BlockObject.y eq block.y)
                        and (BlockObject.z eq block.z))
            }

            if (selectBlocks.none()) {
                BlockObject.insert {
                    it[x] = block.x
                    it[y] = block.y
                    it[z] = block.z
                    it[chunkId] = cId
                }
            }

            BlockObject.selectAll().forEach(::println)
        }
    }
}