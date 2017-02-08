package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.BaseCancellableEvent
import com.masahirosaito.spigot.cuttrees.players.CutTreesPlayer
import com.masahirosaito.spigot.cuttrees.trees.BaseTree

class CutTreesIncrementStaticsEvent(val tree: BaseTree, val player: CutTreesPlayer) : BaseCancellableEvent()