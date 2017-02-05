package com.masahirosaito.spigot.cuttrees

import com.masahirosaito.spigot.cuttrees.configs.Configs
import com.masahirosaito.spigot.cuttrees.configs.ConfigsLoader
import com.masahirosaito.spigot.cuttrees.database.AntiBlockManager
import com.masahirosaito.spigot.cuttrees.listeners.*
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class CutTrees : JavaPlugin() {
    lateinit var configs: Configs
    lateinit var antiBlockManager: AntiBlockManager
    lateinit var messenger: Messenger

    override fun onEnable() {
        configs = Configs(ConfigsLoader.load(File(dataFolder, "config.json")), this)
        messenger = Messenger(this)
        antiBlockManager = AntiBlockManager(this)

        listenerRegister(
                BlockBreakEventListener(this),
                BlockPlaceEventListener(this),
                NoReduceTreeBreakEventListener(this),
                ReduceTreeBreakEventListener(this)
        )

        messenger.debug("Blocks: ${configs.anotherBlockTypeNames}")
        messenger.debug("Tools: ${configs.toolTypeNames}")
    }

    override fun onDisable() {
        antiBlockManager.save()
    }

    private fun listenerRegister(vararg listeners: Listener) {
        listeners.forEach { server.pluginManager.registerEvents(it, this) }
    }
}
