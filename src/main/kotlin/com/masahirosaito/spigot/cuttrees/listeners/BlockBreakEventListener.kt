package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
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

class BlockBreakEventListener(val plugin: CutTrees) : Listener {
    val configs = plugin.configs

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        if (!configs.isValid(event.block)) return
        if (isAnti(event.block)) return
        if (!configs.isValid(event.player.itemInMainHand())) return
        if (!configs.isValid(event.player)) return

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

    private fun isAnti(block: Block): Boolean {
        if (plugin.antiBlockManager.isAnti(block)) {
            plugin.antiBlockManager.remove(block)
            return true
        }

        return false
    }
}