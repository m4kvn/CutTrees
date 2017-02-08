package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.events.CutTreesIncrementStaticsEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class CutTreesIncrementStaticsEventLestener(plugin: CutTrees) : Listener {
    val configs = plugin.configs

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onCutTreesIncrementStatics(event: CutTreesIncrementStaticsEvent) {
        if (event.isCancelled) return

        if (!configs.incrementStatistics || (!configs.onCreativeStatics && event.player.isCreative())) {
            event.isCancelled = true
            return
        }
    }
}