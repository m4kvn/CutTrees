package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.TreeSpecies
import org.bukkit.block.Block

class OakTree(block: Block) : BaseTree(block) {
    val MAX_HEIGHT = 9
    val MIN_HEIGHT = 4

    override fun leavesRange(): Int = 4

    override fun maxLogBranch(): Int = 3

    override fun isValid(blocks: MutableSet<Block>): Boolean {
        return (getTop(blocks).y - getBottom(blocks).y + 1).let {
            println("Tree Height: $it")
            MIN_HEIGHT <= it && it <= MAX_HEIGHT
        }
    }

    override fun isSame(block: Block): Boolean {
        return if (block.isTree()) block.asTree().species == TreeSpecies.GENERIC else false
    }

    override fun isSameLeaves(block: Block): Boolean {
        return if (block.isLeaves()) block.asLeaves().species == TreeSpecies.GENERIC else false
    }
}