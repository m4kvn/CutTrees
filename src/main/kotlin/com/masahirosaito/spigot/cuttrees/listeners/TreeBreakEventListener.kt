package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.CutTreesAbstract
import com.masahirosaito.spigot.cuttrees.events.TreeBreakEvent
import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

abstract class TreeBreakEventListener(plugin: CutTrees) : Listener, CutTreesAbstract(plugin) {

    abstract fun calcToolDamage(tool: ItemStack, blocksSize: Int): Int

    abstract fun calcTakeBlocksSize(blocks: MutableList<Block>, damage: Int, tool: ItemStack): Int

    fun onTreeBreakEvent(event: TreeBreakEvent) {
        if (event.isCancelled) return

        val tool = event.tool
        val blocks = event.calculator.calcRelativeBlocks(event.block)
        val damage = calcToolDamage(tool, blocks.size)

        takeBlocks(blocks, calcTakeBlocksSize(blocks, damage, tool))

        val leaves = event.calculator.calcRelativeLeaves(blocks)
        val blockType = event.block.type

        breakTree(blocks, leaves, tool)

        val durability = tool.getRemainingDurability()
        val player = event.player
        val toolType = tool.type

        damageToTool(tool, damage, player)

        sendBlockBreakMessage(durability, damage, blocks.size, player)

        incrementPlayerStatistics(blockType, blocks, player, toolType)
    }

    private fun incrementPlayerStatistics(blockType: Material, blocks: List<Block>,
                                          player: Player, toolType: Material) {
        if (onIncrementStatistics(player)) {
            player.incrementStatistic(Statistic.MINE_BLOCK, blockType, blocks.size)
            player.incrementStatistic(Statistic.USE_ITEM, toolType, blocks.size)

            messenger.debug(buildString {
                append("${ChatColor.GOLD}")
                append("[統計変更] ")
                append("Player: ${player.name}, ")
                append("MINE_BLOCK: ${player.getStatistic(Statistic.MINE_BLOCK, blockType)}, ")
                append("USE_ITEM: ${player.getStatistic(Statistic.USE_ITEM, toolType)}")
                append("${ChatColor.RESET}")
            })
        }
    }

    private fun breakTree(blocks: MutableList<Block>, leaves: MutableSet<Block>, tool: ItemStack) {
        blocks.forEach { it.breakNaturally(tool) }
        leaves.forEach { it.breakNaturally() }
    }

    private fun takeBlocks(blocks: MutableList<Block>, takeSize: Int) {
        repeat(takeSize, {
            blocks.removeAt(blocks.lastIndex)
        })
    }

    private fun damageToTool(tool: ItemStack, damage: Int, player: Player) {
        if (tool.isBreak(damage)) player.onBreakItemInMainHand()
        else tool.damage(damage)
    }

    private fun sendBlockBreakMessage(durability: Int, damage: Int, blocksNum: Int, player: Player) {
        if (configs.onBlockBreakMessage) {
            val newDurability = if (durability - damage >= 0) durability - damage else 0
            player.sendMessage("耐久値: $durability -> $newDurability, ブロック数: $blocksNum")
        }
    }

    private fun onIncrementStatistics(player: Player): Boolean {
        if (!configs.incrementStatistics) return false
        if (player.isCreativeMode() && !configs.incrementStatisticsCreative) return false
        return true
    }
}