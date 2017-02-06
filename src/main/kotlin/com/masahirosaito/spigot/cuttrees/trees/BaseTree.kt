package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.utils.getRelatives
import org.bukkit.block.Block

abstract class BaseTree(val block: Block) {
    val stem = getStem(block)
    val blocks = getBlocks(stem)
    val leaves = getLeaves(blocks)

    abstract fun leavesRange(): Int

    abstract fun maxLogBranch(): Int

    abstract fun isValid(blocks: MutableSet<Block>): Boolean

    abstract fun isSame(block: Block): Boolean

    abstract fun isSameLeaves(block: Block): Boolean

    fun breakTree(): Boolean {
        if (!isValid(blocks)) return false

        blocks.forEach { it.breakNaturally() }
        leaves.forEach { it.breakNaturally() }

        return true
    }

    fun getStem(block: Block) = getRelativeTrees(block).minBy { it.y } ?: throw Exception()

    fun getBottom(blocks: MutableSet<Block>) = blocks.minBy { it.y } ?: throw Exception()

    fun getTop(blocks: MutableSet<Block>): Block = blocks.maxBy { it.y } ?: throw Exception()

    fun getBlocks(bottom: Block) = getRelativeTrees(bottom)

    fun getLeaves(blocks: MutableSet<Block>): MutableSet<Block> {
        return mutableSetOf<Block>().apply {
            blocks.forEach { block ->
                addAll(block.getRelatives(leavesRange()).filter { isSameLeaves(it) })
            }
        }
    }

    fun getRelativeTrees(block: Block): MutableSet<Block> {
        val unCheckedBlocks = mutableSetOf(block)
        val checkedBlocks = mutableSetOf<Block>()

        while (unCheckedBlocks.isNotEmpty()) {
            unCheckedBlocks.first().let { b ->
                unCheckedBlocks.remove(b)
                checkedBlocks.add(b)
                unCheckedBlocks.addAll(b.getRelatives(1)
                        .filter { isSame(it) }
                        .filter { block.x - maxLogBranch() <= it.x && it.x <= block.x + maxLogBranch() }
                        .filter { block.z - maxLogBranch() <= it.z && it.z <= block.z + maxLogBranch() }
                        .filterNot { checkedBlocks.contains(it) })
            }
        }

        return checkedBlocks
    }
}