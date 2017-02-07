package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.CutTreesAbstract
import com.masahirosaito.spigot.cuttrees.trees.*
import com.masahirosaito.spigot.cuttrees.utils.asMushroom
import com.masahirosaito.spigot.cuttrees.utils.asTree
import com.masahirosaito.spigot.cuttrees.utils.isMushroom
import com.masahirosaito.spigot.cuttrees.utils.isTree
import org.bukkit.Material
import org.bukkit.TreeSpecies
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakEventListener(plugin: CutTrees) : CutTreesAbstract(plugin), Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {

        if (event.isCancelled) return

        if (event.player.isSneaking) return

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
            event.block.isMushroom() -> when (event.block.asMushroom().itemType){
                Material.HUGE_MUSHROOM_1 -> WhiteMushroom(event.block)
                Material.HUGE_MUSHROOM_2 -> RedMushroom(event.block)
                else -> return
            }
            else -> return
        }

        tree.breakTree()



//        if (event.isCancelled) return
//        if (isInValid(event)) return antiBlockManager.remove(event.block)
//
//        debugMsg(event)
//
//        if (isNotReduceDurability(event)) {
//            NoReduceTreeBreakEvent(event, plugin).call(plugin)
//        } else {
//            ReduceTreeBreakEvent(event, plugin).call(plugin)
//        }
    }

//    private fun debugMsg(event: BlockBreakEvent) {
//        messenger.debug(buildString {
//            append("${ChatColor.GOLD}")
//            append("[Valid Event] $event")
//            append("${ChatColor.RESET}")
//        })
//    }
//
//    private fun isNotReduceDurability(event: BlockBreakEvent): Boolean = when {
//        (event.player.isCreativeMode() && !configs.onCreativeDurabilityReduce) -> true
//        (event.player.itemInMainHand().itemMeta.spigot().isUnbreakable) -> true
//        else -> false
//    }
//
//    private fun isInValid(event: BlockBreakEvent): Boolean {
//        return when {
//            !configs.isValidBlock(event.block) -> true.apply { messenger.debug("Block is InValid") }
//            !configs.isNotAnti(event.block) -> true.apply { messenger.debug("Block is AntiBlock") }
//            !configs.isValidTool(event.player.itemInMainHand()) -> true.apply { messenger.debug("Tool is InValid") }
//            !configs.isSneaking(event.player) -> true.apply { messenger.debug("Player is not Sneaking") }
//            else -> false
//        }
//    }
}

