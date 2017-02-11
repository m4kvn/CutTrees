package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.materials.DurabilityMaterial
import com.masahirosaito.spigot.cuttrees.utils.asLeaves
import com.masahirosaito.spigot.cuttrees.utils.asTree
import com.masahirosaito.spigot.cuttrees.utils.isLeaves
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.Material
import org.bukkit.TreeSpecies
import org.bukkit.block.Block

class DarkOakTree(block: Block) : BaseTree(block) {

    override fun growingOn() = arrayOf(
            DurabilityMaterial(Material.DIRT, 0),
            DurabilityMaterial(Material.DIRT, 1),
            DurabilityMaterial(Material.GRASS, 0)
    )

    override fun material() = Material.LOG_2

    override fun heightRange() = 6 to 9

    override fun leavesRange() = 4

    override fun branchRange() = 2

    override fun relativeRange() = 1

    override fun isInValid(blocks: MutableSet<Block>) = false

    override fun isSame(block: Block): Boolean {
        return if (block.isTree()) block.asTree().species == TreeSpecies.DARK_OAK else false
    }

    override fun isSameLeaves(block: Block): Boolean {
        return if (block.isLeaves()) block.asLeaves().species == TreeSpecies.DARK_OAK else false
    }

    override fun bottomsRange() = 1 to 4
}