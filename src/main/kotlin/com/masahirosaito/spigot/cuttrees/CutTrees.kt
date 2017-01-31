package com.masahirosaito.spigot.cuttrees

import com.masahirosaito.spigot.cuttrees.configs.Configs
import com.masahirosaito.spigot.cuttrees.database.AntiBlockManager
import com.masahirosaito.spigot.cuttrees.listeners.*
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class CutTrees : JavaPlugin() {
    lateinit var configs: Configs
    lateinit var antiBlockManager: AntiBlockManager

    override fun onEnable() {
        configs = Configs.load(File(dataFolder, "config.json"))
        antiBlockManager = AntiBlockManager(this)

        listenerRegister(
                BlockBreakEventListener(this),
                BlockPlaceEventListener(this),
                NoReduceTreeBreakEventListener(),
                ReduceTreeBreakEventListener(),
                TreeLeavesDecayEventListener(),
                TreeBreakMessageEventListener()
        )
    }

    override fun onDisable() {
        antiBlockManager.save()
    }

    private fun listenerRegister(vararg listeners: Listener) {
        listeners.forEach { server.pluginManager.registerEvents(it, this) }
    }
}
