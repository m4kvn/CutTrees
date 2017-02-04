package com.masahirosaito.spigot.cuttrees.calculators

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.utils.sameType
import org.bukkit.block.Block

class AnotherBlockCalculator(plugin: CutTrees) : RelativeBlocksCalculator(plugin) {

    override fun relativeBlocksFilter(block: Block, relativeBlock: Block): Boolean {
        return relativeBlock.sameType(block)
    }

    override fun relativeLeavesFilter(block: Block, relativeBlock: Block): Boolean {
        val typeName = configs.anotherBlockTypeNames[block.type.name]
        if (typeName != null) return relativeBlock.type.name == typeName
        else return false
    }
}