package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.utils.getRelatives
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack

abstract class BaseTree(val block: Block) {
    val stem = getStem(block)
    val blocks = getBlocks(stem)
    val leaves = getLeaves(blocks)

    abstract fun maxHeight(): Int

    abstract fun minHeight(): Int

    abstract fun leavesRange(): Int

    abstract fun maxLogBranch(): Int

    abstract fun relativeRange(): Int

    abstract fun isValid(blocks: MutableSet<Block>): Boolean

    abstract fun isSame(block: Block): Boolean

    abstract fun isSameLeaves(block: Block): Boolean

    fun isValidHeight(blocks: MutableSet<Block>): Boolean = height(blocks).let {
        minHeight() <= it && it <= maxHeight()
    }

    fun breakTrees(tool: ItemStack): Boolean = execBreak(blocks, tool)

    fun breakLeaves(tool: ItemStack) = execBreak(leaves, tool)

    fun height(blocks: MutableSet<Block>) = getTop(blocks).y - getBottom(blocks).y + 1

    fun getBottom(blocks: MutableSet<Block>) = blocks.minBy { it.y } ?: throw Exception()

    fun getTop(blocks: MutableSet<Block>): Block = blocks.maxBy { it.y } ?: throw Exception()

    private fun getStem(block: Block) = getRelativeTrees(block).minBy { it.y } ?: throw Exception()

    private fun getBlocks(bottom: Block) = getRelativeTrees(bottom)

    private fun getLeaves(blocks: MutableSet<Block>): MutableSet<Block> {
        return mutableSetOf<Block>().apply {
            blocks.forEach { block ->
                addAll(block.getRelatives(leavesRange()).filter { isSameLeaves(it) })
            }
        }
    }

    private fun getRelativeTrees(block: Block): MutableSet<Block> {
        val unCheckedBlocks = mutableSetOf(block)
        val checkedBlocks = mutableSetOf<Block>()

        while (unCheckedBlocks.isNotEmpty()) {
            unCheckedBlocks.first().let { b ->
                unCheckedBlocks.remove(b)
                checkedBlocks.add(b)
                unCheckedBlocks.addAll(b.getRelatives(relativeRange())
                        .filter { isSame(it) }
                        .filter { block.x - maxLogBranch() <= it.x && it.x <= block.x + maxLogBranch() }
                        .filter { block.z - maxLogBranch() <= it.z && it.z <= block.z + maxLogBranch() }
                        .filterNot { checkedBlocks.contains(it) })
            }
        }

        return checkedBlocks
    }

    private fun execBreak(blocks: MutableSet<Block>, itemStack: ItemStack): Boolean {
        if (!isValidHeight(blocks)) return false
        if (!isValid(blocks)) return false

        blocks.forEach { it.breakNaturally(itemStack) }

        return true
    }
}