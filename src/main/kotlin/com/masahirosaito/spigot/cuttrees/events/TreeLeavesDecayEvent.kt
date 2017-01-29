package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.BaseCancellableEvent
import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.block.Block

class TreeLeavesDecayEvent(val event: TreeBreakEvent) : BaseCancellableEvent() {

    val leaves = mutableSetOf<Block>().apply {
        event.blocks.forEach {
            addAll(it.getRelatives(event.configs.rangeDecayLeaves)
                    .filter(Block::isLeaves)
                    .filter { it.asLeaves().species == event.species })
        }
    }

    fun decayLeaves() = leaves.forEach { it.breakNaturally() }
}