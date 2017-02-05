package com.masahirosaito.spigot.cuttrees.configs

import org.bukkit.Material
import org.bukkit.block.Block

data class DamagedBlock(val str: String) {
    val type = getBlockType(str)
    val damage = getDamage(str)

    private fun getBlockType(str: String): Material = MaterialGetter().get(str.split(":")[0])

    private fun getDamage(str: String): Int {
        return if (str.contains(":")) str.split(":")[1].toInt() else -1
    }

    fun isSameType(block: Block): Boolean {
        return type == block.type
    }

    fun isSameDamage(block: Block): Boolean {
        return if (damage == -1) true else damage.toShort() == block.state.data.toItemStack().durability
    }
}