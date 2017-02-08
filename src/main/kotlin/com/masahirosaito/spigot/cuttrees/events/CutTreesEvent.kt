package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.BaseCancellableEvent
import org.bukkit.event.block.BlockBreakEvent

class CutTreesEvent(val blockBreakEvent: BlockBreakEvent) : BaseCancellableEvent()