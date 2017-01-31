package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.BaseCancellableEvent
import org.bukkit.Statistic

class PlayerStatisticsEvent(event: TreeBreakEvent) : BaseCancellableEvent() {
    val player = event.player
    val blocks = event.blocks
    val tool = event.tool
    val blockType = event.blockType

    fun incrementMineBlock() {
        player.incrementStatistic(Statistic.MINE_BLOCK, blockType, blocks.size)
    }

    fun incrementUseItem() {
        player.incrementStatistic(Statistic.USE_ITEM, tool.type, blocks.size)
    }
}