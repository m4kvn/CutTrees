package com.masahirosaito.spigot.cuttrees

import com.masahirosaito.spigot.cuttrees.configs.Configs
import com.masahirosaito.spigot.cuttrees.database.BlockObject
import com.masahirosaito.spigot.cuttrees.listeners.*
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.createMissingTablesAndColumns
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

class CutTrees : JavaPlugin() {
    lateinit var configs: Configs

    override fun onEnable() {
        configs = Configs.load(File(dataFolder, "config.json"))

        listenerRegister(
                BlockBreakEventListener(this),
                NoReduceTreeBreakEventListener(),
                ReduceTreeBreakEventListener(),
                TreeLeavesDecayEventListener(),
                TreeBreakMessageEventListener(),
                BlockPlaceEventListener()
        )

        Database.connect("jdbc:h2:./${dataFolder.path}/playertrees", driver = "org.h2.Driver")

        transaction {
            createMissingTablesAndColumns(BlockObject)
        }
    }

    private fun listenerRegister(vararg listeners: Listener) {
        listeners.forEach { server.pluginManager.registerEvents(it, this) }
    }
}
