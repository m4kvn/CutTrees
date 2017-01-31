package com.masahirosaito.spigot.cuttrees

import org.bukkit.Bukkit
import org.bukkit.ChatColor

class Messenger(val plugin: CutTrees) {
    val logger = plugin.logger!!

    fun log(obj: Any) {
        Bukkit.getConsoleSender().sendMessage("[${plugin.name}] $obj")
    }

    fun debug(obj: Any) {
        if (plugin.configs.onDebug) log("${ChatColor.AQUA}[DEBUG]${ChatColor.RESET} $obj")
    }
}