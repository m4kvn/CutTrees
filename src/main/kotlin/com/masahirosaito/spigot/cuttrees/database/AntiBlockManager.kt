package com.masahirosaito.spigot.cuttrees.database

import org.bukkit.block.Block
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AntiBlockManager(val plugin: JavaPlugin) {
    val antiBlocks = mutableSetOf<Block>()

    init {
        Database.connect("jdbc:h2:./${plugin.dataFolder.path}/playertrees", driver = "org.h2.Driver")

        transaction {
            SchemaUtils.createMissingTablesAndColumns(BlockObject)

            BlockObject.selectAll().forEach {
                antiBlocks.add(getBlock(it))
            }
        }
    }

    private fun getBlock(result: ResultRow): Block =
            plugin.server.getWorld(result[BlockObject.worldUid]).getBlockAt(
                    result[BlockObject.x],
                    result[BlockObject.y],
                    result[BlockObject.z]
            )

    fun save() {
        transaction {
            antiBlocks.forEach { block ->
                try {
                    BlockObject.insert {
                        it[worldUid] = block.world.uid
                        it[x] = block.x
                        it[y] = block.y
                        it[z] = block.z
                    }
                } catch(e: Exception) {
                }
            }
        }
    }

    fun add(block: Block) {
        antiBlocks.add(block)
    }

    fun remove(block: Block) {
        antiBlocks.remove(block)
    }

    fun isAnti(block: Block): Boolean = antiBlocks.contains(block)
}