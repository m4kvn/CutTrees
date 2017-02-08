package com.masahirosaito.spigot.cuttrees

import com.masahirosaito.spigot.cuttrees.configs.CutTreesConfig
import com.masahirosaito.spigot.cuttrees.listeners.BlockBreakEventListener
import com.masahirosaito.spigot.cuttrees.listeners.CutTreesBreakEventListener
import com.masahirosaito.spigot.cuttrees.listeners.CutTreesIncrementStaticsEventListener
import com.masahirosaito.spigot.cuttrees.listeners.CutTreesToolDamageEventListener
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class CutTrees : JavaPlugin() {
    lateinit var configs: CutTreesConfig
    lateinit var messenger: Messenger

    override fun onEnable() {
        configs = CutTreesConfig.load(File(dataFolder, "config.json"))
        messenger = Messenger(this)

        listenerRegister(
                BlockBreakEventListener(this),
                CutTreesBreakEventListener(this),
                CutTreesToolDamageEventListener(this),
                CutTreesIncrementStaticsEventListener(this)
        )

        messenger.debug("Configs: $configs")
    }

    private fun listenerRegister(vararg listeners: Listener) {
        listeners.forEach { server.pluginManager.registerEvents(it, this) }
    }
}
