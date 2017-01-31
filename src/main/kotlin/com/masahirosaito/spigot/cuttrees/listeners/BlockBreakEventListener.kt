package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.database.BlockObject
import com.masahirosaito.spigot.cuttrees.events.NoReduceTreeBreakEvent
import com.masahirosaito.spigot.cuttrees.events.ReduceTreeBreakEvent
import com.masahirosaito.spigot.cuttrees.events.TreeBreakMessageEvent
import com.masahirosaito.spigot.cuttrees.events.TreeLeavesDecayEvent
import com.masahirosaito.spigot.cuttrees.utils.call
import com.masahirosaito.spigot.cuttrees.utils.isCreativeMode
import com.masahirosaito.spigot.cuttrees.utils.itemInMainHand
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class BlockBreakEventListener(val plugin: CutTrees) : Listener {
    val configs = plugin.configs

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        if (!event.player.isSneaking) return
        if (!configs.isValid(event.block)) return
        if (exists(event.block)) return
        if (!configs.isValid(event.player.itemInMainHand())) return

        val breakEvent = if (isNotReduceDurability(event)) {
            NoReduceTreeBreakEvent(event, plugin).call(plugin)
        } else {
            ReduceTreeBreakEvent(event, plugin).call(plugin)
        }

        if (breakEvent.isCancelled) return

        TreeLeavesDecayEvent(breakEvent).call(plugin)

        TreeBreakMessageEvent(breakEvent).call(plugin)
    }

    private fun isNotReduceDurability(event: BlockBreakEvent): Boolean = when {
        (event.player.isCreativeMode() && !configs.onCreativeDurabilityReduce) -> true
        (event.player.itemInMainHand().itemMeta.spigot().isUnbreakable) -> true
        else -> false
    }

    private fun exists(block: Block): Boolean {
        var exists: Boolean = true
        var selectId: Int

        transaction {
            val selectBlocks = BlockObject.select {
                ((BlockObject.worldUid eq block.world.uid)
                        and (BlockObject.x eq block.x)
                        and (BlockObject.y eq block.y)
                        and (BlockObject.z eq block.z))
            }
            exists = !selectBlocks.none()

            if (exists) {
                selectId = selectBlocks.first()[BlockObject.id]
                BlockObject.deleteWhere { BlockObject.id eq selectId }
            }
        }

        return exists
    }
}