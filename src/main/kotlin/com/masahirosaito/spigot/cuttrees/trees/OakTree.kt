package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.TreeSpecies
import org.bukkit.block.Block

class OakTree(val block: Block) {
    val MAX_HEIGHT = 8
    val MIN_HEIGHT = 4
    val MAX_LOG_BRANCH = 4
    val LEAVES_RANGE = 3
    val SPECIES = TreeSpecies.GENERIC

    fun breakTree(): Boolean {
        val bottom = getBottom(block)
        val blocks = getBlocks(bottom)
        val top = getTop(blocks)

        if (!isValid(bottom, top)) return false

        val leaves = getLeaves(bottom, top, blocks)

        blocks.forEach { it.breakNaturally() }
        leaves.forEach { it.breakNaturally() }

        return true
    }

    fun isValid(bottom: Block, top: Block): Boolean {
        return (top.y - bottom.y).let { MIN_HEIGHT-1 <= it && it <= MAX_HEIGHT+1 }
    }

    fun getBottom(block: Block): Block = getRelativeTrees(block).minBy { it.y } ?: throw Exception()

    fun getTop(blocks: MutableSet<Block>): Block = blocks.maxBy { it.y } ?: throw Exception()

    fun getMinXBlock(blocks: MutableSet<Block>) = blocks.minBy { it.x } ?: throw Exception()

    fun getMaxXBlock(blocks: MutableSet<Block>) = blocks.maxBy { it.x } ?: throw Exception()

    fun getMinZBlock(blocks: MutableSet<Block>) = blocks.minBy { it.z } ?: throw Exception()

    fun getMaxZBlock(blocks: MutableSet<Block>) = blocks.maxBy { it.z } ?: throw Exception()

    fun isSame(block: Block): Boolean = if (block.isTree()) block.asTree().species == SPECIES else false

    fun isSameLeaves(block: Block): Boolean = if (block.isLeaves()) block.asLeaves().species == SPECIES else false

    fun getBlocks(bottom: Block) = getRelativeTrees(bottom)

    fun getLeaves(bottom: Block, top: Block, blocks: MutableSet<Block>): List<Block> {
        val maxX = getMaxXBlock(blocks).x + LEAVES_RANGE
        val minX = getMinXBlock(blocks).x - LEAVES_RANGE
        val maxZ = getMaxZBlock(blocks).z + LEAVES_RANGE
        val minZ = getMinZBlock(blocks).z - LEAVES_RANGE
        val maxY = top.y + LEAVES_RANGE
        val minY = bottom.y

        return mutableListOf<Block>().apply {
            for (x in minX..maxX) for (z in minZ..maxZ) for (y in minY..maxY)
                add(blocks.first().world.getBlockAt(x, y, z))
        }.filter { isSameLeaves(it) }
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