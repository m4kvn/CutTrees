package com.masahirosaito.spigot.cuttrees.events

import com.masahirosaito.spigot.cuttrees.BaseCancellableEvent
import com.masahirosaito.spigot.cuttrees.tools.CutTreesTool
import com.masahirosaito.spigot.cuttrees.trees.BaseTree

class CutTreesToolDamageEvent(val tree: BaseTree, val tool: CutTreesTool) : BaseCancellableEvent()