package com.masahirosaito.spigot.cuttrees.calculators

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.block.Block

class TreeCalculator(plugin: CutTrees) : RelativeBlocksCalculator(plugin) {

    override fun relativeBlocksFilter(block: Block, relativeBlock: Block): Boolean {
        return when {
            !relativeBlock.isTree() -> false
            !relativeBlock.asTree().sameSpecies(block.asTree()) -> false
            else -> true
        }
    }

    override fun relativeLeavesFilter(block: Block, relativeBlock: Block): Boolean {
        return when {
            !relativeBlock.isLeaves() -> false
            !relativeBlock.asLeaves().sameSpecies(block.asTree()) -> false
            else -> true
        }
    }
}