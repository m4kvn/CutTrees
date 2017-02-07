package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.utils.asLeaves
import com.masahirosaito.spigot.cuttrees.utils.asTree
import com.masahirosaito.spigot.cuttrees.utils.isLeaves
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.TreeSpecies
import org.bukkit.block.Block

class BirchTree(block: Block) : BaseTree(block) {

    override fun maxHeight(): Int = 7

    override fun minHeight(): Int = 4

    override fun leavesRange(): Int = 4

    override fun maxLogBranch(): Int = 0

    override fun relativeRange(): Int = 1

    override fun isValid(blocks: MutableSet<Block>): Boolean = true

    override fun isSame(block: Block): Boolean {
        return if (block.isTree()) block.asTree().species == TreeSpecies.BIRCH else false
    }

    override fun isSameLeaves(block: Block): Boolean {
        return if (block.isLeaves()) block.asLeaves().species == TreeSpecies.BIRCH else false
    }
}