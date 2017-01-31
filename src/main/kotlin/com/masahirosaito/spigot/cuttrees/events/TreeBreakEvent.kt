package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.BaseCancellableEvent
import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.block.Block
import org.bukkit.event.block.BlockBreakEvent

abstract class TreeBreakEvent(event: BlockBreakEvent, val plugin: CutTrees) : BaseCancellableEvent() {
    val configs = plugin.configs
    var blocks = calcBreakBLocks(event.block)
    val player = event.player!!
    val tool = player.itemInMainHand()
    val durability = tool.getRemainingDurability()
    var damage = blocks.size
    val species = event.block.asTree().species!!
    val blockType = event.block.type!!

    private fun calcBreakBLocks(block: Block): MutableSet<Block> {
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
                    .filter { isNotAntiBlock(it) }
                    .filterNot { checkedBlocks.contains(it) }
            )
        }
        return checkedBlocks
    }

    fun breakBlocks() = blocks.forEach { it.breakNaturally(tool) }

    private fun isNotAntiBlock(block: Block): Boolean = !plugin.antiBlockManager.isAnti(block)
}