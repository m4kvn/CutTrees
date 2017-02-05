package com.masahirosaito.spigot.cuttrees.configs

import org.bukkit.Material

class BlockTypesLoader(val blockTypeNames: Map<String, String>) {

    fun load(): MutableMap<DamagebleBlock, DamagebleBlock?> {
        return mutableMapOf<DamagebleBlock, DamagebleBlock?>().apply {
            blockTypeNames.forEach { pair ->
                try {
                    put(getBlock(pair.key), getLeaves(pair.value))
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getBlock(str: String): DamagebleBlock {
        return DamagebleBlock(str)
    }

    private fun getLeaves(str: String): DamagebleBlock? {
        try {
            return if (str.isNullOrBlank()) null else DamagebleBlock(str)
        } catch(e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    class DamagebleBlock(str: String) {
        val type = getBlockType(str)
        val damage = getDamage(str)

        private fun getBlockType(str: String): Material = MaterialGetter().get(str.split(":")[0])

        private fun getDamage(str: String): Short {
            return if (str.contains(":")) str.split(":")[1].toShort() else -1
        }
    }
}