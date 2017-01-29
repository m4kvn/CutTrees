package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.events.TreeLeavesDecayEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class TreeLeavesDecayEventListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onTreeLeavesDecay(event: TreeLeavesDecayEvent) {
        if (event.isNotCancelled) event.decayLeaves()
    }
}