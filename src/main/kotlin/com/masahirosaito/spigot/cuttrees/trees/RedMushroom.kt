package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.materials.DurabilityMaterial
import com.masahirosaito.spigot.cuttrees.utils.asMushroom
import com.masahirosaito.spigot.cuttrees.utils.isMushroom
import org.bukkit.Material
import org.bukkit.block.Block

class RedMushroom(block: Block) : BaseTree(block) {

    override fun growingOn() = arrayOf(
            DurabilityMaterial(Material.DIRT, 2)
    )

    override fun material() = Material.HUGE_MUSHROOM_2

    override fun heightRange() = 5 to 13

    override fun leavesRange() = 0

    override fun branchRange() = 2

    override fun relativeRange() = 1

    override fun isInValid(blocks: MutableSet<Block>) = false

    override fun isSame(block: Block): Boolean {
        return if (block.isMushroom()) block.asMushroom().itemType == material() else false
    }

    override fun isSameLeaves(block: Block) = false

    override fun bottomsRange() = 1 to 1
}