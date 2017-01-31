package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import com.masahirosaito.spigot.cuttrees.events.PlayerStatisticsEvent
import com.masahirosaito.spigot.cuttrees.utils.isCreativeMode
import org.bukkit.ChatColor
import org.bukkit.Statistic
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority

class PlayerStatisticsEventListener(plugin: CutTrees) : CutTreesListener(plugin) {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerStatistics(event: PlayerStatisticsEvent) {
        when {
            event.isCancelled -> return
            !configs.incrementStatistics -> return
            event.player.isCreativeMode() && !configs.incrementStatisticsCreative -> return
        }

        event.incrementMineBlock()
        event.incrementUseItem()

        debugStatistic(event)
    }

    private fun debugStatistic(event: PlayerStatisticsEvent) {
        messenger.debug(buildString {
            append("${ChatColor.GOLD}")
            append("[統計変更] ")
            append("Player: ${event.player.name}, ")
            append("MINE_BLOCK: ${event.player.getStatistic(Statistic.MINE_BLOCK, event.blockType)}, ")
            append("USE_ITEM: ${event.player.getStatistic(Statistic.USE_ITEM, event.tool.type)}")
            append("${ChatColor.RESET}")
        })
    }
}