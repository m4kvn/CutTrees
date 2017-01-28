package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.events.ReduceTreeBreakEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class ReduceTreeBreakEventListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onReduceTreeBreak(event: ReduceTreeBreakEvent) {

        if (event.isCancelled) return

        event.calcDurabilityDamage()

        event.takeBlocks()

        event.breakBlocks()

        event.onDamage()
    }
}