package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.events.*
import com.masahirosaito.spigot.cuttrees.utils.call
import com.masahirosaito.spigot.cuttrees.utils.isCreativeMode
import com.masahirosaito.spigot.cuttrees.utils.itemInMainHand
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakEventListener(plugin: CutTrees) : CutTreesListener(plugin) {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        when {
            event.isCancelled -> return
            !configs.isValid(event.block) -> return
            isAnti(event.block) -> return
            !configs.isValid(event.player.itemInMainHand()) -> return
            !configs.isValid(event.player) -> return
        }

        val breakEvent = if (isNotReduceDurability(event)) {
            NoReduceTreeBreakEvent(event, plugin).call(plugin)
        } else {
            ReduceTreeBreakEvent(event, plugin).call(plugin)
        }

        if (breakEvent.isCancelled) return

        TreeLeavesDecayEvent(breakEvent).call(plugin)

        TreeBreakMessageEvent(breakEvent).call(plugin)

        PlayerStatisticsEvent(breakEvent).call(plugin)
    }

    private fun isNotReduceDurability(event: BlockBreakEvent): Boolean = when {
        (event.player.isCreativeMode() && !configs.onCreativeDurabilityReduce) -> true
        (event.player.itemInMainHand().itemMeta.spigot().isUnbreakable) -> true
        else -> false
    }

    private fun isAnti(block: Block): Boolean {
        if (antiBlockManager.isAnti(block)) {
            antiBlockManager.remove(block)
            return true
        }

        return false
    }
}