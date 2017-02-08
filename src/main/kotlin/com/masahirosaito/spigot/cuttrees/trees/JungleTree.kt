package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.utils.asLeaves
import com.masahirosaito.spigot.cuttrees.utils.asTree
import com.masahirosaito.spigot.cuttrees.utils.isLeaves
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.TreeSpecies
import org.bukkit.block.Block

class JungleTree(block: Block) : BaseTree(block) {

    override fun material(): Material = Material.LOG

    override fun maxHeight(): Int = block.world.maxHeight

    override fun minHeight(): Int = 4

    override fun leavesRange(): Int = 5

    override fun maxLogBranch(): Int = 5

    override fun relativeRange(): Int = 2

    override fun isValid(blocks: MutableSet<Block>): Boolean = true

    override fun isSame(block: Block): Boolean {
        return if (block.isTree()) block.asTree().species == TreeSpecies.JUNGLE else false
    }

    override fun isSameLeaves(block: Block): Boolean {
        return if (block.isLeaves()) block.asLeaves().species == TreeSpecies.JUNGLE else false
    }
}