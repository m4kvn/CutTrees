package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.Material
import org.bukkit.Sound
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
        when {
            !configs.isValid(event.block) -> return
            !configs.isValid(event.player.itemInMainHand()) -> return
        }

        var blocks = calcBreakBLocks(event.block)
        val player = event.player
        val tool = player.itemInMainHand()

        if (tool.isBraek(blocks.size)) {
            blocks = blocks.take(tool.getRemainingDurability())
        }

        blocks.forEach { it.breakNaturally(event.player.itemInMainHand()) }

        if (tool.isBraek(blocks.size)) {
            player.world.playSound(player.location, Sound.ENTITY_ITEM_BREAK, 1f, 1f)
            player.inventory.itemInMainHand = ItemStack(Material.AIR)
        } else {
            tool.durability = tool.durability.plus(blocks.size).toShort()
        }
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

    fun register() = plugin.server.pluginManager.registerEvents(this, plugin)
}