package com.masahirosaito.spigot.cuttrees.listeners

import com.masahirosaito.spigot.cuttrees.CutTrees
import org.bukkit.event.Listener

abstract class CutTreesListener(val plugin: CutTrees) : Listener {
    val configs = plugin.configs
    val antiBlockManager = plugin.antiBlockManager
    val messenger = plugin.messenger
}