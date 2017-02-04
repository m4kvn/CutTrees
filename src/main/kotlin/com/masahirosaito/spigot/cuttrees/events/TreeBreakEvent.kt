package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.BaseCancellableEvent
import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.calculators.AnotherBlockCalculator
import com.masahirosaito.spigot.cuttrees.calculators.MushroomCalculator
import com.masahirosaito.spigot.cuttrees.calculators.RelativeBlocksCalculator
import com.masahirosaito.spigot.cuttrees.calculators.TreeCalculator
import com.masahirosaito.spigot.cuttrees.utils.isMushroom
import com.masahirosaito.spigot.cuttrees.utils.isTree
import com.masahirosaito.spigot.cuttrees.utils.itemInMainHand
import org.bukkit.block.Block
import org.bukkit.event.block.BlockBreakEvent

abstract class TreeBreakEvent(event: BlockBreakEvent, val plugin: CutTrees) : BaseCancellableEvent() {
    val block = event.block!!
    val calculator = getCalculator(block)
    val player = event.player!!
    val tool = player.itemInMainHand()

    private fun getCalculator(block: Block): RelativeBlocksCalculator = when {
        block.isTree() -> TreeCalculator(plugin)
        block.isMushroom() -> MushroomCalculator(plugin)
        else -> AnotherBlockCalculator(plugin)
    }
}