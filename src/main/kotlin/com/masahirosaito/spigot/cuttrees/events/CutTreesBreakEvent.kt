package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.BaseCancellableEvent
import com.masahirosaito.spigot.cuttrees.trees.BaseTree
import org.bukkit.entity.Player

class CutTreesBreakEvent(val tree: BaseTree, val player: Player) : BaseCancellableEvent()