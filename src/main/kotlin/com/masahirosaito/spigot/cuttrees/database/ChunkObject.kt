package com.masahirosaito.spigot.cuttrees.database

import org.jetbrains.exposed.sql.Table

object ChunkObject : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val worldUid = uuid("world_uuid")
    val x = integer("x")
    val z = integer("z")
}