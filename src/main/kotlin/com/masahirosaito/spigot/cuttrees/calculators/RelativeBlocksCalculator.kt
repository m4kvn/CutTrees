package com.masahirosaito.spigot.cuttrees.calculators

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.CutTreesAbstract
import com.masahirosaito.spigot.cuttrees.utils.getRelatives
import org.bukkit.block.Block

abstract class RelativeBlocksCalculator(plugin: CutTrees) : CutTreesAbstract(plugin) {

    fun calcRelativeBlocks(block: Block): MutableList<Block> {
        val unCheckedBlocks = mutableSetOf(block)
        val checkedBlocks = mutableSetOf<Block>()

        while (unCheckedBlocks.isNotEmpty() && configs.isNotMax(checkedBlocks)) {
            val b = unCheckedBlocks.first().apply {
                unCheckedBlocks.remove(this)
                checkedBlocks.add(this)
            }
            unCheckedBlocks.addAll(b.getRelatives(configs.rangeBreakBlock)
                    .filter { relativeBlocksFilter(block, it) }
                    .filterNot { antiBlockManager.isAnti(it) }
                    .filterNot { checkedBlocks.contains(it) }
            )
        }
        return checkedBlocks.toMutableList()
    }

    fun calcRelativeLeaves(blocks: MutableList<Block>): MutableSet<Block> {
        return mutableSetOf<Block>().apply {
            blocks.forEach { block ->
                addAll(block.getRelatives(configs.rangeDecayLeaves)
                        .filter { relativeLeavesFilter(block, it) })
            }
        }
    }

    abstract fun relativeBlocksFilter(block: Block, relativeBlock: Block): Boolean

    abstract fun relativeLeavesFilter(block: Block, relativeBlock: Block): Boolean
}