package com.masahirosaito.spigot.cuttrees.database

import org.jetbrains.exposed.sql.Table

object BlockObject : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val x = integer("x")
    val y = integer("y")
    val z = integer("z")
    val chunkId = integer("chunk_id").references(ChunkObject.id)
}