package com.masahirosaito.spigot.cuttrees.trees

import com.masahirosaito.spigot.cuttrees.materials.DurabilityMaterial
import com.masahirosaito.spigot.cuttrees.tools.CutTreesTool
import com.masahirosaito.spigot.cuttrees.utils.getRelatives
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

abstract class BaseTree(brokenBlock: Block) {
    val bottoms = getBottoms(brokenBlock)
    val blocks = getBlocks(bottoms)
    val leaves = getLeaves(blocks)

    abstract fun growingOn(): Array<DurabilityMaterial>

    abstract fun material(): Material

    abstract fun heightRange(): Pair<Int, Int>

    abstract fun leavesRange(): Int

    abstract fun branchRange(): Int

    abstract fun relativeRange(): Int

    abstract fun isInValid(blocks: MutableSet<Block>): Boolean

    abstract fun isSame(block: Block): Boolean

    abstract fun isSameLeaves(block: Block): Boolean

    abstract fun bottomsRange(): Pair<Int, Int>

    fun isValid(): Boolean {
        return when {
            !isValidBottomsSize() -> false
            !isValidGrowing() -> false
            !isValidHeight() -> false
            isInValid(blocks) -> false
            else -> true
        }
    }

    fun breakTree(tool: CutTreesTool) {
        blocks.forEach { it.breakNaturally(tool.itemStack) }
        leaves.forEach { it.breakNaturally() }
    }

    fun height() = maxYBlock(blocks).y - minYBlock(blocks).y + 1

    fun minYBlock(blocks: Collection<Block>) = (blocks.minBy { it.y } ?: throw Exception())

    fun maxYBlock(blocks: Collection<Block>) = (blocks.maxBy { it.y } ?: throw Exception())

    fun maxX(blocks: Collection<Block>) = (blocks.maxBy { it.x } ?: throw Exception()).x

    fun minX(blocks: Collection<Block>) = (blocks.minBy { it.x } ?: throw Exception()).x

    fun maxZ(blocks: Collection<Block>) = (blocks.maxBy { it.z } ?: throw Exception()).z

    fun minZ(blocks: Collection<Block>) = (blocks.minBy { it.z } ?: throw Exception()).z

    private fun getLeaves(blocks: MutableSet<Block>): MutableSet<Block> {
        return mutableSetOf<Block>().apply {
            blocks.forEach { block ->
                addAll(block.getRelatives(leavesRange()).filter { isSameLeaves(it) })
            }
        }
    }

    private fun relativeBlocks(blocks: Collection<Block>,
                               filter: (Collection<Block>, Block) -> Boolean): MutableSet<Block> {
        val unCheckedBlocks = mutableSetOf<Block>().apply { addAll(blocks) }
        val checkedBlocks = mutableSetOf<Block>()

        while (unCheckedBlocks.isNotEmpty()) {
            unCheckedBlocks.first().let { b ->
                unCheckedBlocks.remove(b)
                checkedBlocks.add(b)
                unCheckedBlocks.addAll(b.getRelatives(relativeRange())
                        .filter { isSame(it) }
                        .filter { filter(blocks, it) }
                        .filterNot { checkedBlocks.contains(it) })
            }
        }

        return checkedBlocks
    }

    private fun getBottom(block: Block): Block {
        return minYBlock(relativeBlocks(listOf(block), fun(bs: Collection<Block>, fb: Block): Boolean {
            return (bs.first().x - branchRange() <= fb.x && fb.x <= bs.first().x + branchRange()) &&
                    (bs.first().z - branchRange() <= fb.z && fb.z <= bs.first().z + branchRange())
        }))
    }

    private fun getBottoms(block: Block): MutableSet<Block> {
        return relativeBlocks(listOf(getBottom(block)), fun(bs: Collection<Block>, fb: Block): Boolean {
            return fb.y == bs.first().y
        })
    }

    private fun getBlocks(bottoms: MutableSet<Block>): MutableSet<Block> {
        return relativeBlocks(bottoms, fun(bs: Collection<Block>, fb: Block): Boolean {
            return (minX(bs) - branchRange() <= fb.x && fb.x <= maxX(bs) + branchRange()) &&
                    (minZ(bs) - branchRange() <= fb.z && fb.z <= maxZ(bs) + branchRange())
        })
    }

    private fun isValidHeight() = height().let { heightRange().first <= it && it <= heightRange().second }

    private fun isValidGrowing(): Boolean {
        return bottoms.forEach { bottom ->
            DurabilityMaterial.new(bottom.getRelative(BlockFace.DOWN)).let {
                if (!growingOn().contains(it)) return false
            }
        }.let { true }
    }

    private fun isValidBottomsSize() = bottomsRange().first <= bottoms.size && bottoms.size <= bottomsRange().second
}