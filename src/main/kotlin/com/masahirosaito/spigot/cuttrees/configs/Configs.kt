package com.masahirosaito.spigot.cuttrees.configs

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.utils.isMushroom
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.block.Block
import org.bukkit.entity.Player

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

    fun isValidBlock(block: Block): Boolean = when {
        block.isTree() -> true
        block.isMushroom() -> true
        else -> false
    }

    fun isSneaking(player: Player): Boolean = if (onSneaking) player.isSneaking else true

    fun isNotMax(blocks: Collection<Block>): Boolean = blocks.size < maxBlockAmount
}