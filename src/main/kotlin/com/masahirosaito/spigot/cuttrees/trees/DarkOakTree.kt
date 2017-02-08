package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.utils.asLeaves
import com.masahirosaito.spigot.cuttrees.utils.asTree
import com.masahirosaito.spigot.cuttrees.utils.isLeaves
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.Material
import org.bukkit.TreeSpecies
import org.bukkit.block.Block

class DarkOakTree(block: Block) : BaseTree(block) {

    override fun material(): Material = Material.LOG_2

    override fun maxHeight(): Int = 9

    override fun minHeight(): Int = 6

    override fun leavesRange(): Int = 4

    override fun maxLogBranch(): Int = 4

    override fun relativeRange(): Int = 1

    override fun isValid(blocks: MutableSet<Block>): Boolean = true

    override fun isSame(block: Block): Boolean {
        return if (block.isTree()) block.asTree().species == TreeSpecies.DARK_OAK else false
    }

    override fun isSameLeaves(block: Block): Boolean {
        return if (block.isLeaves()) block.asLeaves().species == TreeSpecies.DARK_OAK else false
    }
}