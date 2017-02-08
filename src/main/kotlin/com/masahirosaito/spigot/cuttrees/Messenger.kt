package com.masahirosaito.spigot.cuttrees

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class Messenger(val plugin: CutTrees) {
    val logger = plugin.logger!!

    fun prefix(obj: Any) = "[${plugin.name}] $obj"

    fun log(obj: Any) {
        Bukkit.getConsoleSender().sendMessage(prefix(obj))
    }

    fun debug(obj: Any) {
        if (plugin.configs.onDebug) log("${ChatColor.AQUA}[DEBUG]${ChatColor.RESET} $obj")
    }

    fun send(player: Player, obj: Any) {
        player.sendMessage(prefix(obj))
    }
}