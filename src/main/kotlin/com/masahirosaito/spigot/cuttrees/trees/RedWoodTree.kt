package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.materials.DurabilityMaterial
import com.masahirosaito.spigot.cuttrees.utils.asLeaves
import com.masahirosaito.spigot.cuttrees.utils.asTree
import com.masahirosaito.spigot.cuttrees.utils.isLeaves
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.Material
import org.bukkit.TreeSpecies
import org.bukkit.block.Block

class RedWoodTree(block: Block) : BaseTree(block) {

    override fun growingOn() = arrayOf(
            DurabilityMaterial(Material.DIRT, 0),
            DurabilityMaterial(Material.DIRT, 1),
            DurabilityMaterial(Material.GRASS, 0)
    )

    override fun material(): Material = Material.LOG

    override fun maxHeight(): Int = 29

    override fun minHeight(): Int = 4

    override fun leavesRange(): Int = 4

    override fun maxLogBranch(): Int = 1

    override fun relativeRange(): Int = 1

    override fun isInValid(blocks: MutableSet<Block>): Boolean = false

    override fun isSame(block: Block): Boolean {
        return if (block.isTree()) block.asTree().species == TreeSpecies.REDWOOD else false
    }

    override fun isSameLeaves(block: Block): Boolean {
        return if (block.isLeaves()) block.asLeaves().species == TreeSpecies.REDWOOD else false
    }
}