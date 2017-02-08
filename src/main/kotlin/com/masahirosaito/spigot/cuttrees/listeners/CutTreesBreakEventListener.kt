package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.events.CutTreesBreakEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class CutTreesBreakEventListener(plugin: CutTrees) : Listener {
    val configs = plugin.configs

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onCutTreesBreak(event: CutTreesBreakEvent) {
        if (event.isCancelled) return

        if ((!configs.onCreative && event.player.isCreative())
                || (configs.onSneaking && event.player.isSneaking())) {
            return event.cancel()
        }
    }
}