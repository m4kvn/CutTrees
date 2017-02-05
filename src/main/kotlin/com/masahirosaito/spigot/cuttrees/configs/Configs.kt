package com.masahirosaito.spigot.cuttrees.configs

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.utils.isMushroom
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Configs(loader: ConfigsLoader, val plugin: CutTrees) {
    val onDebug = loader.onDebug
    val onCreativeDurabilityReduce = loader.onCreativeDurabilityReduce
    val onBlockBreakMessage = loader.onBlockBreakMessage
    val maxBlockAmount = loader.maxBlockAmount
    val rangeBreakBlock = loader.rangeBreakBlock
    val rangeDecayLeaves = loader.rangeDecayLeaves
    val onSneaking = loader.onSneaking
    val incrementStatistics = loader.incrementStatistics
    val incrementStatisticsCreative = loader.incrementStatisticsCreative
    val onAntiBLock = loader.onAntiBLock
    val toolTypeNames = ToolTypesLoader(loader.toolTypeNames).load()
    val anotherBlockTypeNames = BlockTypesLoader(loader.anotherBlockTypeNames).load()

    fun isValidBlock(block: Block): Boolean = when {
        block.isTree() -> true
        block.isMushroom() -> true
        isAnotherBlock(block) -> true
        else -> false
    }

    fun isValidTool(tool: ItemStack): Boolean = toolTypeNames.contains(tool.type)

    fun isSneaking(player: Player): Boolean = if (onSneaking) player.isSneaking else true

    fun isNotMax(blocks: Collection<Block>): Boolean = blocks.size < maxBlockAmount

    fun isNotAnti(block: Block): Boolean = !isAnti(block)

    fun isAnti(block: Block): Boolean = if (onAntiBLock) plugin.antiBlockManager.isAnti(block) else false

    fun isAnotherBlock(block: Block): Boolean = getDamagedBlock(block) != null

    fun isAnotherLeaves(block: Block, leaves: Block): Boolean = getDamagedLeaves(block, leaves) != null

    fun getDamagedBlock(block: Block): DamagedBlock? {
        anotherBlockTypeNames.forEach { pair ->
            if (pair.key.isSameType(block)) {
                if (pair.key.isSameDamage(block)) return pair.key
            }
        }
        return null
    }

    fun getDamagedLeaves(block: Block, leaves: Block): DamagedBlock? {
        if (isAnotherBlock(block)) {
            anotherBlockTypeNames[getDamagedBlock(block)].let {
                if (it != null) if (it.isSameType(leaves)) if (it.isSameDamage(leaves)) return it
            }
        }
        return null
    }
}