package com.masahirosaito.spigot.cuttrees.utils

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

fun <T : Listener> T.register(plugin: JavaPlugin) = apply { plugin.server.pluginManager.registerEvents(this, plugin) }