package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockPlaceEvent

class BlockPlaceEventListener(plugin: CutTrees) : CutTreesListener(plugin) {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBLockPlace(event: BlockPlaceEvent) {
        when {
            event.isCancelled -> return
            !configs.isValid(event.block) -> return
        }
        antiBlockManager.add(event.block)
    }
}