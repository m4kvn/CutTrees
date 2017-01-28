package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

class BlockBreakEventListener(val plugin: CutTrees) : Listener {
    val configs = plugin.configs

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (!isValid(event)) return

        var blocks = calcBreakBLocks(event.block)
        val player = event.player
        val tool = player.itemInMainHand()

        if (player.isCreativeMode() && !configs.onCreativeDurabilityReduce) {
            breakBlocks(blocks, tool)
            return
        }

        if (tool.isBreak(blocks.size)) {
            blocks = blocks.take(tool.getRemainingDurability())
        }

        breakBlocks(blocks, tool)

        if (tool.isBreak(blocks.size)) {
            player.onBreakItemInMainHand()
        } else {
            tool.damage(blocks.size)
        }
    }

    private fun isValid(event: BlockBreakEvent): Boolean = when {
        !configs.isValid(event.block) -> false
        !configs.isValid(event.player.itemInMainHand()) -> false
        else -> true
    }

    private fun calcBreakBLocks(block: Block): List<Block> {
        val unCheckedBlocks = mutableSetOf(block)
        val checkedBlocks = mutableSetOf<Block>()

        while (unCheckedBlocks.isNotEmpty() && configs.isNotMax(checkedBlocks)) {
            val b = unCheckedBlocks.first().apply {
                unCheckedBlocks.remove(this)
                checkedBlocks.add(this)
            }
            unCheckedBlocks.addAll(b.getRelatives(configs.rangeBreakBlock)
                    .filter(Block::isTree)
                    .filter { b.asTree().sameSpecies(block.asTree()) }
                    .filterNot { checkedBlocks.contains(it) }
            )
        }
        return checkedBlocks.toList()
    }

    private fun breakBlocks(blocks: Collection<Block>, tool: ItemStack) {
        blocks.forEach { it.breakNaturally(tool) }
    }

    fun register() = plugin.server.pluginManager.registerEvents(this, plugin)
}