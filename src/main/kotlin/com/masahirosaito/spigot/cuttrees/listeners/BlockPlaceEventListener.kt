package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class BlockPlaceEventListener(val plugin: CutTrees) : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBLockPlace(event: BlockPlaceEvent) {
        if (event.isCancelled) return
        if (!plugin.configs.isValid(event.block)) return

        plugin.antiBlockManager.add(event.block)
    }
}