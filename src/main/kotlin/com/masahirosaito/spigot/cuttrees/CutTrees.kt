package com.masahirosaito.spigot.cuttrees

import com.masahirosaito.spigot.cuttrees.configs.Configs
import com.masahirosaito.spigot.cuttrees.configs.ConfigsLoader
import com.masahirosaito.spigot.cuttrees.listeners.BlockBreakEventListener
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class CutTrees : JavaPlugin() {
    lateinit var configs: Configs
    lateinit var messenger: Messenger

    override fun onEnable() {
        configs = Configs(ConfigsLoader.load(File(dataFolder, "config.json")), this)
        messenger = Messenger(this)

        listenerRegister(
                BlockBreakEventListener(this)
        )
    }

    private fun listenerRegister(vararg listeners: Listener) {
        listeners.forEach { server.pluginManager.registerEvents(it, this) }
    }
}
