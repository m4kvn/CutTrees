package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.events.CutTreesBreakEvent
import com.masahirosaito.spigot.cuttrees.trees.*
import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.Material
import org.bukkit.TreeSpecies
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

class BlockBreakEventListener(val plugin: CutTrees) : Listener {
    val configs = plugin.configs

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {

        if (event.isCancelled) return

        if (!event.player.itemInMainHand().isAxe()) return

        val tree = when {
            event.block.isTree() -> when (event.block.asTree().species) {
                TreeSpecies.GENERIC -> OakTree(event.block)
                TreeSpecies.JUNGLE -> JungleTree(event.block)
                TreeSpecies.DARK_OAK -> DarkOakTree(event.block)
                TreeSpecies.BIRCH -> BirchTree(event.block)
                TreeSpecies.ACACIA -> AcaciaTree(event.block)
                TreeSpecies.REDWOOD -> RedWoodTree(event.block)
                else -> return
            }
            event.block.isMushroom() -> when (event.block.asMushroom().itemType) {
                Material.HUGE_MUSHROOM_1 -> WhiteMushroom(event.block)
                Material.HUGE_MUSHROOM_2 -> RedMushroom(event.block)
                else -> return
            }
            else -> return
        }

        val breakEvent = CutTreesBreakEvent(tree, event.player).call(plugin).apply {
            if (isCancelled) return

            tree.breakTrees(player.itemInMainHand())
            tree.breakLeaves(player.itemInMainHand())
        }
    }

    fun ItemStack.isAxe() = when (type) {
        Material.WOOD_AXE,
        Material.STONE_AXE,
        Material.GOLD_AXE,
        Material.IRON_AXE,
        Material.DIAMOND_AXE -> true
        else -> false
    }
}

