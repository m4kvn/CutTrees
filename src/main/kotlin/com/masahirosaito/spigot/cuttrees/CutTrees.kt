package com.masahirosaito.spigot.cuttrees

import com.masahirosaito.spigot.cuttrees.configs.Configs
import com.masahirosaito.spigot.cuttrees.listeners.BlockBreakEventListener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class CutTrees : JavaPlugin() {
    lateinit var configs: Configs

    override fun onEnable() {
        configs = Configs.load(File(dataFolder, "config.json"))

        BlockBreakEventListener(this).register()
    }
}
