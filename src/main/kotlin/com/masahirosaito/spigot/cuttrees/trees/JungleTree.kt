package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.utils.asLeaves
import com.masahirosaito.spigot.cuttrees.utils.asTree
import com.masahirosaito.spigot.cuttrees.utils.isLeaves
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.TreeSpecies
import org.bukkit.block.Block

class JungleTree(block: Block) : BaseTree(block) {
    val MIN_HEIGHT = 4

    override fun leavesRange(): Int = 5

    override fun maxLogBranch(): Int = 5

    override fun isValid(blocks: MutableSet<Block>): Boolean {
        return (getTop(blocks).y - getBottom(blocks).y + 1).let { MIN_HEIGHT <= it }
    }

    override fun isSame(block: Block): Boolean {
        return if (block.isTree()) block.asTree().species == TreeSpecies.JUNGLE else false
    }

    override fun isSameLeaves(block: Block): Boolean {
        return if (block.isLeaves()) block.asLeaves().species == TreeSpecies.JUNGLE else false
    }
}