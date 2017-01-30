package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.database.BlockObject
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.material.Tree
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class BlockPlaceEventListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBLockPlace(event: BlockPlaceEvent) {
        if (event.isCancelled) return
        if (event.block.state.data !is Tree) return

        val block = event.block

        transaction {
            val selectBlocks = BlockObject.select {
                ((BlockObject.x eq block.x)
                        and (BlockObject.y eq block.y)
                        and (BlockObject.z eq block.z)
                        and (BlockObject.worldUid eq block.world.uid))
            }

            if (selectBlocks.none()) {
                BlockObject.insert {
                    it[x] = block.x
                    it[y] = block.y
                    it[z] = block.z
                    it[worldUid] = block.world.uid
                }
            }
        }
    }
}