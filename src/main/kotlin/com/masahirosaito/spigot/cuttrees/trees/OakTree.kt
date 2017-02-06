package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.utils.asTree
import com.masahirosaito.spigot.cuttrees.utils.getRelatives
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.TreeSpecies
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

class OakTree(val block: Block) {
    val MAX_HEIGHT = 8
    val MIN_HEIGHT = 4
    val MAX_LOG_BRANCH = 4
    val bottom = getBottom(block)
    val top = getTop(bottom)

    fun isValid(): Boolean = (top.y - bottom.y).let { MIN_HEIGHT <= it && it <= MAX_HEIGHT }

    fun getBottom(block: Block): Block = getRelativeTrees(block).minBy { it.y } ?: throw Exception()

    fun getTop(bottom: Block): Block = getMaxUpperBlock(bottom)

    fun isSame(block: Block): Boolean = if (block.isTree()) block.asTree().species == TreeSpecies.GENERIC else false

    fun getRelativeTrees(block: Block): MutableSet<Block> {
        val unCheckedBlocks = mutableListOf(block)
        val checkedBlocks = mutableSetOf<Block>()

        while (unCheckedBlocks.isNotEmpty()) {
            unCheckedBlocks.removeAt(0).let { b ->
                checkedBlocks.add(b)
                unCheckedBlocks.addAll(b.getRelatives(1)
                        .filter { isSame(it) }
                        .filter { block.x - MAX_LOG_BRANCH <= it.x && it.x <= block.x + MAX_LOG_BRANCH }
                        .filter { block.z - MAX_LOG_BRANCH <= it.z && it.z <= block.z + MAX_LOG_BRANCH }
                        .filterNot { checkedBlocks.contains(it) })
            }
        }

        return checkedBlocks
    }

    fun getMaxUpperBlock(block: Block): Block {
        var b = block
        var upper = block.getRelative(BlockFace.UP)

        while (isSame(upper)) {
            b = upper
            upper = b.getRelative(BlockFace.UP)
        }

        return b
    }
}