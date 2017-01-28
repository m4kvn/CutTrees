package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.events.TreeBreakMessageEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class TreeBreakMessageEventListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onTreeBreakMessage(event: TreeBreakMessageEvent) {
        if (event.isNotCancelled) event.sendBlockBreakMessage()
    }
}