package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.CutTrees
import org.bukkit.event.block.BlockBreakEvent

class NoReduceTreeBreakEvent(event: BlockBreakEvent, plugin: CutTrees) : TreeBreakEvent(event, plugin)