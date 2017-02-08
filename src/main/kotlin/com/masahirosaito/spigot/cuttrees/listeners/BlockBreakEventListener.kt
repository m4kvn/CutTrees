package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.events.CutTreesBreakEvent
import com.masahirosaito.spigot.cuttrees.events.CutTreesEvent
import com.masahirosaito.spigot.cuttrees.events.CutTreesIncrementStaticsEvent
import com.masahirosaito.spigot.cuttrees.events.CutTreesToolDamageEvent
import com.masahirosaito.spigot.cuttrees.players.CutTreesPlayer
import com.masahirosaito.spigot.cuttrees.trees.*
import com.masahirosaito.spigot.cuttrees.utils.*
import org.bukkit.Material
import org.bukkit.TreeSpecies
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakEventListener(val plugin: CutTrees) : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return

        CutTreesEvent(event).call(plugin).apply { if (isCancelled) return }

        val player = CutTreesPlayer(event.player).apply { if (!isValid()) return }

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

        CutTreesBreakEvent(tree, player).call(plugin).apply { if (isCancelled) return }

        tree.breakTrees(player.tool)
        tree.breakLeaves()

        if (CutTreesToolDamageEvent(tree, player).call(plugin).isNotCancelled) {
            player.DamageToTool(tree)
        }

        if (CutTreesIncrementStaticsEvent(tree, player).call(plugin).isNotCancelled) {
            player.incrementStatics(tree)
        }
    }
}

