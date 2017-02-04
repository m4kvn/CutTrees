package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.CutTreesAbstract
import com.masahirosaito.spigot.cuttrees.events.*
import com.masahirosaito.spigot.cuttrees.utils.call
import com.masahirosaito.spigot.cuttrees.utils.isCreativeMode
import com.masahirosaito.spigot.cuttrees.utils.itemInMainHand
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakEventListener(plugin: CutTrees) : CutTreesAbstract(plugin), Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        when {
            event.isCancelled -> return
            !configs.isValid(event.block) -> return
            isAnti(event.block) -> return
            !configs.isValid(event.player.itemInMainHand()) -> return
            !configs.isValid(event.player) -> return
        }

        if (isNotReduceDurability(event)) {
            NoReduceTreeBreakEvent(event, plugin).call(plugin)
        } else {
            ReduceTreeBreakEvent(event, plugin).call(plugin)
        }
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