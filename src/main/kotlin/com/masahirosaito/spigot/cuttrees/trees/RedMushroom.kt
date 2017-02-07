package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.utils.asMushroom
import com.masahirosaito.spigot.cuttrees.utils.isMushroom
import org.bukkit.Material
import org.bukkit.block.Block

class RedMushroom(block: Block) : BaseTree(block) {

    override fun maxHeight(): Int = 11

    override fun minHeight(): Int = 5

    override fun leavesRange(): Int = 0

    override fun maxLogBranch(): Int = 2

    override fun relativeRange(): Int = 1

    override fun isValid(blocks: MutableSet<Block>): Boolean = true

    override fun isSame(block: Block): Boolean {
        return if (block.isMushroom()) block.asMushroom().itemType == Material.HUGE_MUSHROOM_2 else false
    }

    override fun isSameLeaves(block: Block): Boolean = false
}