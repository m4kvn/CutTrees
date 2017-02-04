package com.masahirosaito.spigot.cuttrees

abstract class CutTreesAbstract(val plugin: CutTrees) {
    val configs = plugin.configs
    val antiBlockManager = plugin.antiBlockManager
    val messenger = plugin.messenger
}