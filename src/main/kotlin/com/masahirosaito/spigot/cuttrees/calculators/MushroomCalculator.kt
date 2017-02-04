package com.masahirosaito.spigot.cuttrees.calculators

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.block.Block

class MushroomCalculator(plugin: CutTrees) : RelativeBlocksCalculator(plugin) {

    override fun relativeBlocksFilter(block: Block, relativeBlock: Block): Boolean {
        return when {
            !relativeBlock.isMushroom() -> false
            !relativeBlock.sameType(block) -> false
            else -> true
        }
    }

    override fun relativeLeavesFilter(block: Block, relativeBlock: Block): Boolean {
        return false
    }
}