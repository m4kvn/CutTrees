package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.TreeSpecies
import org.bukkit.block.Block

class OakTree(val block: Block) {
    val MAX_HEIGHT = 9
    val MIN_HEIGHT = 4
    val MAX_LOG_BRANCH = 4
    val LEAVES_RANGE = 3
    val SPECIES = TreeSpecies.GENERIC

    fun breakTree(): Boolean {
        val stem = getStem(block)
        val blocks = getBlocks(stem)

        if (!isValid(blocks)) return false

        val leaves = getLeaves(blocks)

        blocks.forEach { it.breakNaturally() }
        leaves.forEach { it.breakNaturally() }

        return true
    }

    fun isValid(blocks: MutableSet<Block>): Boolean {
        return (getTop(blocks).y - getBottom(blocks).y + 1).let {
            println("Tree Height: $it")
            MIN_HEIGHT <= it && it <= MAX_HEIGHT
        }
    }

    fun getStem(block: Block) = getRelativeTrees(block).minBy { it.y } ?: throw Exception()

    fun getBottom(blocks: MutableSet<Block>) = blocks.minBy { it.y } ?: throw Exception()

    fun getTop(blocks: MutableSet<Block>): Block = blocks.maxBy { it.y } ?: throw Exception()

    fun isSame(block: Block): Boolean = if (block.isTree()) block.asTree().species == SPECIES else false

    fun isSameLeaves(block: Block): Boolean = if (block.isLeaves()) block.asLeaves().species == SPECIES else false

    fun getBlocks(bottom: Block) = getRelativeTrees(bottom)

    fun getLeaves(blocks: MutableSet<Block>): MutableSet<Block> {
        return mutableSetOf<Block>().apply {
            blocks.forEach { block ->
                addAll(block.getRelatives(LEAVES_RANGE).filter { isSameLeaves(it) })
            }
        }
    }

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
}