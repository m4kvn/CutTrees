package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.events.CutTreesToolDamageEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class CutTreesToolDamageEventListener(plugin: CutTrees) : Listener {
    val configs = plugin.configs

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onCutTreesToolDamage(event: CutTreesToolDamageEvent) {
        if (event.isCancelled) return

        if (!configs.onCreativeDurability && event.player.isCreative()) {
            return event.cancel()
        }
    }
}