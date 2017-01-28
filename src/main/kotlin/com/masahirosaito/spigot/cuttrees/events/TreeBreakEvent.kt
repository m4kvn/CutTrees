package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.BaseCancellableEvent
import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.block.Block
import org.bukkit.event.block.BlockBreakEvent

abstract class TreeBreakEvent(event: BlockBreakEvent, plugin: CutTrees) : BaseCancellableEvent() {
    val configs = plugin.configs
    var blocks = calcBreakBLocks(event.block)
    val player = event.player!!
    val tool = player.itemInMainHand()
    val durability = tool.getRemainingDurability()
    var damage = blocks.size

    private fun calcBreakBLocks(block: Block): List<Block> {
        val unCheckedBlocks = mutableSetOf(block)
        val checkedBlocks = mutableSetOf<Block>()

        while (unCheckedBlocks.isNotEmpty() && configs.isNotMax(checkedBlocks)) {
            val b = unCheckedBlocks.first().apply {
                unCheckedBlocks.remove(this)
                checkedBlocks.add(this)
            }
            unCheckedBlocks.addAll(b.getRelatives(configs.rangeBreakBlock)
                    .filter(Block::isTree)
                    .filter { b.asTree().sameSpecies(block.asTree()) }
                    .filterNot { checkedBlocks.contains(it) }
            )
        }
        return checkedBlocks.toList()
    }

    fun breakBlocks() = blocks.forEach { it.breakNaturally(tool) }
}