package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.events.NoReduceTreeBreakEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class NoReduceTreeBreakEventListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onNoReduceTreeBreak(event: NoReduceTreeBreakEvent) {

        if (event.isCancelled) return

        event.damage = 0

        event.breakBlocks()
    }
}