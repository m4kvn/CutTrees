package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.materials.DurabilityMaterial
import com.masahirosaito.spigot.cuttrees.utils.asLeaves
import com.masahirosaito.spigot.cuttrees.utils.asTree
import com.masahirosaito.spigot.cuttrees.utils.isLeaves
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.TreeSpecies
import org.bukkit.block.Block

class JungleTree(block: Block) : BaseTree(block) {

    override fun growingOn() = arrayOf(
            DurabilityMaterial(Material.DIRT, 0),
            DurabilityMaterial(Material.DIRT, 1),
            DurabilityMaterial(Material.GRASS, 0)
    )

    override fun material() = Material.LOG

    override fun heightRange() = 4 to height()

    override fun leavesRange() = 5

    override fun branchRange() = 5

    override fun relativeRange() = 1

    override fun isInValid(blocks: MutableSet<Block>) = false

    override fun isSame(block: Block): Boolean {
        return if (block.isTree()) block.asTree().species == TreeSpecies.JUNGLE else false
    }

    override fun isSameLeaves(block: Block): Boolean {
        return if (block.isLeaves()) block.asLeaves().species == TreeSpecies.JUNGLE else false
    }

    override fun bottomsRange() = 1 to 4
}