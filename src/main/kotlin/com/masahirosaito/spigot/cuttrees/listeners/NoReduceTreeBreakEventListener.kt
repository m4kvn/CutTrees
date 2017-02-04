package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.events.NoReduceTreeBreakEvent
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.inventory.ItemStack

class NoReduceTreeBreakEventListener(plugin: CutTrees) : TreeBreakEventListener(plugin) {

    override fun calcToolDamage(tool: ItemStack, blocksSize: Int): Int = 0

    override fun calcTakeBlocksSize(blocks: MutableList<Block>, damage: Int, tool: ItemStack): Int = 0

    @EventHandler(priority = EventPriority.MONITOR)
    fun onNoReduceTreeBreak(event: NoReduceTreeBreakEvent) {
        onTreeBreakEvent(event)
    }
}