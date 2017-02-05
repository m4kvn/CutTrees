package com.masahirosaito.spigot.cuttrees.database

import com.masahirosaito.spigot.cuttrees.CutTrees
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Block
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AntiBlockManager(val plugin: CutTrees) {
    val antiBlocks = mutableSetOf<Block>()

    init {
        Database.connect("jdbc:h2:./${plugin.dataFolder.path}/playertrees", driver = "org.h2.Driver")

        transaction {
            SchemaUtils.createMissingTablesAndColumns(BlockObject)

            BlockObject.selectAll().forEach {
                antiBlocks.add(getBlock(it))
            }
        }
        sendLog("Load")
    }

    private fun getBlock(result: ResultRow): Block {
        return plugin.server.getWorld(result[BlockObject.worldUid]).getBlockAt(
                result[BlockObject.x],
                result[BlockObject.y],
                result[BlockObject.z]
        )
    }

    fun save() {
        transaction {
            try {
                BlockObject.deleteAll()
            } catch(e: Exception) {
            }
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
        sendLog("Save")
    }

    fun add(block: Block) {
        if (antiBlocks.add(block)) {
            sendDebug(block, "Add")
        }
    }

    fun remove(block: Block) {
        if (antiBlocks.remove(block)) {
            sendDebug(block, "Remove")
        }
    }

    fun isAnti(block: Block): Boolean {
        return antiBlocks.contains(block)
    }

    private fun sendDebug(block: Block, action: String) {
        plugin.messenger.debug(buildString {
            append("${ChatColor.GOLD}")
            append("[$action AntiBlock] $block")
            append("${ChatColor.RESET}")
        })
    }

    private fun sendLog(action: String) {
        plugin.messenger.log(buildString {
            append("${ChatColor.GOLD}")
            append("[$action AntiBlock] complete")
            append("${ChatColor.RESET}")
        })
    }
}